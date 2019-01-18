package com.auctioncorp.auctionapp.controller;

import java.util.ArrayList;
import java.util.List;

import com.auctioncorp.auctionapp.model.AuctionItem;
import com.auctioncorp.auctionapp.model.Bid;
import com.auctioncorp.auctionapp.model.Item;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuctionController {

    private List<AuctionItem> auctions;

    public AuctionController() {
        this.auctions = new ArrayList<AuctionItem>();
        AuctionItem item = new AuctionItem(1000.00, new Item("abcd", "I'm a description"));
        item.setAuctionItemId("1234");
        auctions.add(item);
    }

    @GetMapping("/auctionitems")
    public List<AuctionItem> getAllAuctions() {
        return auctions;
    }

    @GetMapping("/auctionitems/{auctionitemid}")
    public AuctionItem getAuctionById(@PathVariable String auctionitemid) {
        for (int i = 0; i < auctions.size(); i++) {
            if (auctions.get(i).getAuctionItemId().equals(auctionitemid)) {

                return auctions.get(i);
            }
        }
        return null;
    }

    @PostMapping("/auctionitems")
    @ResponseBody
    public String addAuctionItem(@RequestBody AuctionItem item) {
        item.setAuctionItemId("09281");
        auctions.add(item);
        return item.getAuctionItemId();
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