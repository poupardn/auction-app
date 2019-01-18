package com.auctioncorp.auctionapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.auctioncorp.auctionapp.model.AuctionItem;
import com.auctioncorp.auctionapp.model.Bid;
import com.auctioncorp.auctionapp.model.Item;
import com.auctioncorp.auctionapp.repository.AuctionRepository;
import com.google.common.collect.Lists;

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

    private List<AuctionItem> auctions;

    public AuctionController() {
        this.auctions = new ArrayList<AuctionItem>();
        AuctionItem item = new AuctionItem(1000.00, new Item("abcd", "I'm a description"));
        item.setAuctionItemId("1234");
        auctions.add(item);
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
        AuctionItem auctionToUpdate;
        for (int i = 0; i < auctions.size(); i++) {
            if (auctions.get(i).getAuctionItemId().equals(bid.getAuctionItemId())) {

                auctionToUpdate = auctions.get(i);
            }
        }

        return "";
    }

}