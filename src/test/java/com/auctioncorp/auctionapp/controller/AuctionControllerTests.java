package com.auctioncorp.auctionapp.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.auctioncorp.auctionapp.AuctionApplication;
import com.auctioncorp.auctionapp.repository.AuctionRepository;
import com.auctioncorp.auctionapp.repository.BidRepository;
import com.auctioncorp.auctionapp.model.AuctionItem;
import com.auctioncorp.auctionapp.model.Item;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert.*;
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
import org.springframework.core.ParameterizedTypeReference;

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
}