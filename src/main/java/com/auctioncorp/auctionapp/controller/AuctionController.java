package com.auctioncorp.auctionapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.auctioncorp.auctionapp.model.AuctionItem;
import com.auctioncorp.auctionapp.model.Bid;
import com.auctioncorp.auctionapp.model.Item;
import com.auctioncorp.auctionapp.repository.AuctionRepository;
import com.auctioncorp.auctionapp.repository.BidRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Doubles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuctionController {

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    BidRepository bidRepository;

    private List<AuctionItem> auctions;

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
        return newItem.get();
    }

    @PostMapping("/auctionitems")
    @ResponseBody
    public String addAuctionItem(@RequestBody AuctionItem item) {
        item.setAuctionItemId(UUID.randomUUID().toString());
        auctionRepository.save(item);
        Optional<AuctionItem> newItem = auctionRepository.findById(item.getAuctionItemId());
        return newItem.get().getAuctionItemId();
    }

    @PostMapping("/bids")
    public String postBid(@RequestBody Bid bid) {
        Double incomingMaxBid = bid.getMaxAutoBidAmount();
        AuctionItem item = auctionRepository.findById(bid.getAuctionItemId()).get();
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
            Double currentMaxBid = maxBid.getMaxAutoBidAmount();
            if (incomingMaxBid > currentMaxBid) {
                currentBid = currentMaxBid + 1.00;
                maxBidder = bid.getBidderName();
            } else {
                currentBid = incomingMaxBid + 1.00;
                maxBidder = item.getCurrentBidder();
            }
            bidRepository.save(bid);
            item.setCurrentBid(currentBid);
            item.setCurrentBidder(maxBidder);
            auctionRepository.save(item);
        }

        return "";
    }

}