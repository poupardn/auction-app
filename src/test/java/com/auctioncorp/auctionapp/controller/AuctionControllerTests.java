package com.auctioncorp.auctionapp.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.auctioncorp.auctionapp.AuctionApplication;
import com.auctioncorp.auctionapp.repository.AuctionRepository;
import com.auctioncorp.auctionapp.repository.BidRepository;
import com.auctioncorp.auctionapp.model.AuctionItem;
import com.auctioncorp.auctionapp.model.Item;
import com.auctioncorp.auctionapp.model.Bid;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Doubles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuctionApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuctionControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    BidRepository bidRepository;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Before
    public void init() {

    }

    public String getUrl(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void getAuctionById() {
        String auctionItemId = UUID.randomUUID().toString();
        try {
            AuctionItem item = new AuctionItem(1000.00, new Item("test item", "a description"));
            item.setAuctionItemId(auctionItemId);
            auctionRepository.save(item);
            HttpEntity<String> entity = new HttpEntity<String>(null, headers);
            ResponseEntity<String> response = restTemplate.exchange(getUrl("/auctionitems/" + auctionItemId),
                    HttpMethod.GET, entity, String.class);
            JSONObject respJson = new JSONObject(response.getBody());

            assertTrue(respJson.getString("auctionItemId").equals(auctionItemId));
        } finally {
            auctionRepository.deleteById(auctionItemId);
            if (auctionRepository.findById(auctionItemId).isPresent()) {
                fail();
            }
        }
    }

    @Test
    public void getAuctions() {
        String auctionItemId = UUID.randomUUID().toString();
        try {
            AuctionItem item = new AuctionItem(1000.00, new Item("test item", "a description"));
            item.setAuctionItemId(auctionItemId);
            auctionRepository.save(item);
            HttpEntity<String> entity = new HttpEntity<String>(null, headers);
            ResponseEntity<String> response = restTemplate.exchange(getUrl("/auctionitems"), HttpMethod.GET, entity,
                    String.class);
            String resp = response.getBody();

            // This is probably slow on large datasets, but Redis stores hash items in
            // insertion order. Worst case you could also supply a different
            // configuration for the tests.
            assertTrue(resp.toString().contains(auctionItemId));
        } finally {
            auctionRepository.deleteById(auctionItemId);
            if (auctionRepository.findById(auctionItemId).isPresent()) {
                fail();
            }
        }
    }

    @Test
    public void addAuction() {
        String auctionItemId = null;
        try {
            JSONObject jsonObj = new JSONObject().put("reservePrice", 10450.00).put("item",
                    new JSONObject().put("itemid", "abcd").put("description", "item description"));
            String reqJson = jsonObj.toString();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(reqJson, headers);

            ResponseEntity<String> response = restTemplate.exchange(getUrl("/auctionitems"), HttpMethod.POST, entity,
                    String.class);
            JSONObject respJson = new JSONObject(response.getBody());
            String actual = response.getBody();
            auctionItemId = respJson.getString("auctionItemId");
            System.out.println(respJson.get("auctionItemId"));
            assertTrue(actual.contains("auctionItemId"));

        } finally {
            if (auctionItemId != null) {
                auctionRepository.deleteById(auctionItemId);
                if (auctionRepository.findById(auctionItemId).isPresent()) {
                    fail();
                }
            }
        }
    }

    @Test
    public void singleBidTest() {
        String auctionItemId = UUID.randomUUID().toString();
        List<Bid> bids = new ArrayList<Bid>();
        try {
            // Set up data
            AuctionItem item = new AuctionItem(1000.00, new Item("test item", "a description"));
            item.setAuctionItemId(auctionItemId);
            auctionRepository.save(item);

            // Set up Bid Request
            JSONObject jsonObj = new JSONObject().put("auctionItemId", auctionItemId).put("maxAutoBidAmount", 2000.00)
                    .put("bidderName", "test bidder");
            String reqJson = jsonObj.toString();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(reqJson, headers);

            ResponseEntity<String> response = restTemplate.exchange(getUrl("/bids"), HttpMethod.POST, entity,
                    String.class);

            // Check status code
            HttpStatus statusCode = response.getStatusCode();
            assertEquals(statusCode, HttpStatus.OK);

            // Ensure bid was written to the database
            bids = bidRepository.findByAuctionItemId(auctionItemId);
            if (bids.size() != 1) {
                fail("Incorrect number of bids");
            }

            // check the current bid, should be equal to reserve price
            AuctionItem updateItem = auctionRepository.findById(auctionItemId).get();
            assertEquals(updateItem.getCurrentBid(), item.getReservePrice(), 0.00);
        } finally {
            if (bids.size() > 0) {
                bidRepository.deleteAll(bids);
            }
            auctionRepository.deleteById(auctionItemId);
        }
    }

    @Test
    public void didNotMeetReserveTest() {
        String auctionItemId = UUID.randomUUID().toString();
        List<Bid> bids = new ArrayList<Bid>();
        try {
            // Set up data
            AuctionItem item = new AuctionItem(1000.00, new Item("test item", "a description"));
            item.setAuctionItemId(auctionItemId);
            auctionRepository.save(item);

            // Set up Bid Request
            JSONObject jsonObj = new JSONObject().put("auctionItemId", auctionItemId).put("maxAutoBidAmount", 500.00)
                    .put("bidderName", "test bidder");
            String reqJson = jsonObj.toString();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(reqJson, headers);
            ResponseEntity<String> response = restTemplate.exchange(getUrl("/bids"), HttpMethod.POST, entity,
                    String.class);

            // Check status code and message
            HttpStatus statusCode = response.getStatusCode();
            assertEquals(statusCode, HttpStatus.BAD_REQUEST);
            JSONObject respJson = new JSONObject(response.getBody());
            assertEquals(respJson.get("message"), "Current Bid does not meet the item's reserved price.");
            // Ensure bid was written to the daabase
            bids = bidRepository.findByAuctionItemId(auctionItemId);
            assertEquals(0, bids.size());

        } finally {
            if (bids.size() > 0) {
                bidRepository.deleteAll(bids);
            }
            auctionRepository.deleteById(auctionItemId);
        }
    }

    @Test
    /* Tests multiple Bid scenario where existing bidder has higher max bid */
    public void multipleBidsDBHigher() {
        String auctionItemId = UUID.randomUUID().toString();
        List<Bid> bids = new ArrayList<Bid>();
        try {
            // Set up data
            AuctionItem item = new AuctionItem(1000.00, new Item("test item", "a description"));
            item.setAuctionItemId(auctionItemId);
            item.setCurrentBid(1200.00);
            item.setCurrentBidder("testBidder1");
            auctionRepository.save(item);
            Bid bid1 = new Bid(auctionItemId, 1500.00, "testBidder1");
            // add some irrelevant bids to ensure sort in the Controller works.
            Bid bid2 = new Bid(auctionItemId, 1100.00, "irrelevantBidder1");
            Bid bid3 = new Bid(auctionItemId, 1150.00, "irrelevantBidder2");
            bidRepository.save(bid1);
            bidRepository.save(bid2);
            bidRepository.save(bid3);
            // Set up Intitial Bid Request
            JSONObject jsonObj = new JSONObject().put("auctionItemId", auctionItemId).put("maxAutoBidAmount", 1250.00)
                    .put("bidderName", "testBidder2");
            String reqJson = jsonObj.toString();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(reqJson, headers);
            ResponseEntity<String> response = restTemplate.exchange(getUrl("/bids"), HttpMethod.POST, entity,
                    String.class);

            // Check status code
            HttpStatus statusCode = response.getStatusCode();
            assertEquals(statusCode, HttpStatus.OK);

            // Check Bidder and Current bid amount
            AuctionItem updateItem = auctionRepository.findById(auctionItemId).get();
            assertEquals("testBidder1", updateItem.getCurrentBidder());
            assertEquals(1251.00, updateItem.getCurrentBid(), 0.00);

            bids = bidRepository.findByAuctionItemId(auctionItemId);

        } finally {
            if (bids.size() > 0) {
                bidRepository.deleteAll(bids);
            }
            auctionRepository.deleteById(auctionItemId);
        }
    }

    @Test
    /* Tests multiple Bid scenario where the incoming bidder has higher max bid */
    public void multipleBidsIncomingHigher() {
        String auctionItemId = UUID.randomUUID().toString();
        List<Bid> bids = new ArrayList<Bid>();
        try {
            // Set up data
            AuctionItem item = new AuctionItem(1000.00, new Item("test item", "a description"));
            item.setAuctionItemId(auctionItemId);
            item.setCurrentBid(1200.00);
            item.setCurrentBidder("testBidder1");
            auctionRepository.save(item);
            Bid bid1 = new Bid(auctionItemId, 1250.00, "testBidder1");
            // add some irrelevant bids to ensure sort in the Controller works.
            Bid bid2 = new Bid(auctionItemId, 1100.00, "irrelevantBidder1");
            Bid bid3 = new Bid(auctionItemId, 1150.00, "irrelevantBidder2");
            bidRepository.save(bid1);
            bidRepository.save(bid2);
            bidRepository.save(bid3);
            // Set up Intitial Bid Request
            JSONObject jsonObj = new JSONObject().put("auctionItemId", auctionItemId).put("maxAutoBidAmount", 1500.00)
                    .put("bidderName", "testBidder2");
            String reqJson = jsonObj.toString();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(reqJson, headers);
            ResponseEntity<String> response = restTemplate.exchange(getUrl("/bids"), HttpMethod.POST, entity,
                    String.class);

            // Check status code
            HttpStatus statusCode = response.getStatusCode();
            assertEquals(statusCode, HttpStatus.OK);

            // Check Bidder and Current bid amount
            AuctionItem updateItem = auctionRepository.findById(auctionItemId).get();
            assertEquals("testBidder2", updateItem.getCurrentBidder());
            assertEquals(1251.00, updateItem.getCurrentBid(), 0.00);

            bids = bidRepository.findByAuctionItemId(auctionItemId);

        } finally {
            if (bids.size() > 0) {
                bidRepository.deleteAll(bids);
            }
            auctionRepository.deleteById(auctionItemId);
        }
    }

    @Test
    /* Tests when user enters a new maximum bid */
    public void newMaximumBid() {
        String auctionItemId = UUID.randomUUID().toString();
        List<Bid> bids = new ArrayList<Bid>();
        try {
            // Set up data
            AuctionItem item = new AuctionItem(1000.00, new Item("test item", "a description"));
            item.setAuctionItemId(auctionItemId);
            item.setCurrentBid(1200.00);
            item.setCurrentBidder("testBidder1");
            auctionRepository.save(item);
            Bid bid1 = new Bid(auctionItemId, 1250.00, "testBidder1");
            // add some irrelevant bids to ensure sort in the Controller works.
            Bid bid2 = new Bid(auctionItemId, 1100.00, "irrelevantBidder1");
            Bid bid3 = new Bid(auctionItemId, 1150.00, "irrelevantBidder2");
            bidRepository.save(bid1);
            bidRepository.save(bid2);
            bidRepository.save(bid3);
            // Set up Intitial Bid Request
            JSONObject jsonObj = new JSONObject().put("auctionItemId", auctionItemId).put("maxAutoBidAmount", 1500.00)
                    .put("bidderName", "testBidder1");
            String reqJson = jsonObj.toString();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(reqJson, headers);
            ResponseEntity<String> response = restTemplate.exchange(getUrl("/bids"), HttpMethod.POST, entity,
                    String.class);

            // Check status code
            HttpStatus statusCode = response.getStatusCode();
            assertEquals(statusCode, HttpStatus.OK);

            // Check Bidder and Current bid amount
            AuctionItem updateItem = auctionRepository.findById(auctionItemId).get();
            assertEquals("testBidder1", updateItem.getCurrentBidder());
            assertEquals(1200.00, updateItem.getCurrentBid(), 0.00);

            bids = bidRepository.findByAuctionItemId(auctionItemId);
            Ordering<Bid> orderingByMaxBidAmount = new Ordering<Bid>() {
                @Override
                public int compare(Bid b1, Bid b2) {
                    return Doubles.compare(b2.getMaxAutoBidAmount(), b1.getMaxAutoBidAmount());
                }
            };
            bids.sort(orderingByMaxBidAmount);

            assertEquals(1500.00, bids.get(0).getMaxAutoBidAmount(), 0.00);
        } finally {
            if (bids.size() > 0) {
                bidRepository.deleteAll(bids);
            }
            auctionRepository.deleteById(auctionItemId);
        }
    }
}