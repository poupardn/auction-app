package com.auctioncorp.auctionapp.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.auctioncorp.auctionapp.model.AuctionItem;
import com.auctioncorp.auctionapp.model.Bid;
import com.auctioncorp.auctionapp.model.Message;
import com.auctioncorp.auctionapp.repository.AuctionRepository;
import com.auctioncorp.auctionapp.repository.BidRepository;
import com.auctioncorp.auctionapp.repository.MessageRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Doubles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class AuctionController {

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    BidRepository bidRepository;

    @Autowired
    MessageRepository messageRepository;

    public AuctionController() {

    }

    @GetMapping("/auctionitems")
    public List<AuctionItem> getAllAuctions() {
        List<AuctionItem> auctions = Lists.newArrayList(auctionRepository.findAll());
        return auctions;
    }

    @GetMapping("/auctionitems/{auctionitemid}")
    public AuctionItem getAuctionById(@PathVariable String auctionitemid) {
        Optional<AuctionItem> newItem = auctionRepository.findById(auctionitemid);
        // Not found, so we use Spring's ResponseStatusException for this simple
        // controller.
        if (!newItem.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Auction Not Found");
        }
        return newItem.get();
    }

    @GetMapping("/messages/{bidderName}")
    public Message getMessageById(@PathVariable String bidderName) {
        Optional<Message> message = messageRepository.findById(bidderName);
        return message.get();
    }

    @DeleteMapping("/messages/{bidderName}")
    public void deleteMessageById(@PathVariable String bidderName) {
        Optional<Message> message = messageRepository.findById(bidderName);
        messageRepository.delete(message.get());
    }

    @PostMapping("/auctionitems")
    @ResponseBody
    public String addAuctionItem(@RequestBody AuctionItem item) {
        item.setAuctionItemId(UUID.randomUUID().toString());
        auctionRepository.save(item);
        // Rather than just return the Id, we should ensure we actually saved it.
        Optional<AuctionItem> newItem = auctionRepository.findById(item.getAuctionItemId());
        if (!newItem.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Auction was unable to be added. A database error occurred.");
        }
        return newItem.get().getAuctionItemId();
    }

    @PostMapping("/bids")
    public String postBid(@RequestBody Bid bid) {
        Double incomingMaxBid = bid.getMaxAutoBidAmount();
        // Get the current auction item and bids. Ensure the auction exists and the
        // incoming max is greater than the reserve price.
        AuctionItem item = auctionRepository.findById(bid.getAuctionItemId()).get();
        if (item == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Auction Not Found");
        }
        if (incomingMaxBid < item.getReservePrice()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Current Bid does not meet the item's reserved price.");
        }
        Double currentBid = item.getCurrentBid();
        List<Bid> currentBids = Lists.newArrayList(bidRepository.findAll());
        bid.setBidId(UUID.randomUUID().toString());
        // Check to make sure we have no keys. So, no bid key exists, or if one does
        // exist, then it's first element is null (redis "quirk")
        if (currentBids == null || currentBids.size() == 0 || currentBids.get(0) == null) {
            bidRepository.save(bid);
            item.setCurrentBid(currentBid);
            item.setCurrentBidder(bid.getBidderName());
            auctionRepository.save(item);
        } else {
            // We have bids, so we need to find the maximum bid. If there is more than one
            // bid, we need to find the maximum bid of the current collection. Redis stores
            // the values in insertion order, so we use a sort.
            Bid maxBid = new Bid();
            if (currentBids.size() != 1) {

                Ordering<Bid> orderingByMaxBidAmount = new Ordering<Bid>() {
                    @Override
                    public int compare(Bid b1, Bid b2) {
                        return Doubles.compare(b1.getMaxAutoBidAmount(), b2.getMaxAutoBidAmount());
                    }
                };
                currentBids.sort(orderingByMaxBidAmount);
            }
            maxBid = currentBids.get(0);
            String maxBidder = "";
            String outBid = "";
            Double currentMaxBid = maxBid.getMaxAutoBidAmount();
            // Now we make check to see if the maximum bid from the user is bigger than the
            // highest bidder's maximum. In either case, we increment the lower bid by 1.00
            // and set the current bid.
            if (incomingMaxBid > currentMaxBid) {
                currentBid = currentMaxBid + 1.00;
                maxBidder = bid.getBidderName();
                outBid = item.getCurrentBidder();
            } else {
                currentBid = incomingMaxBid + 1.00;
                maxBidder = item.getCurrentBidder();
                outBid = bid.getBidderName();
            }
            // Now we save the bid to our repository. This serves as our "Audit log" for all
            // accepted bids.
            Message message = new Message(outBid, "You've been outbid on item: " + item.getItem().getItemId());
            messageRepository.save(message);
            bidRepository.save(bid);
            item.setCurrentBid(currentBid);
            item.setCurrentBidder(maxBidder);
            auctionRepository.save(item);
        }
        // API spec shows no return value.
        return "";
    }

}