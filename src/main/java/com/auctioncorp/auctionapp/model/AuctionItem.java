package com.auctioncorp.auctionapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("auctionitem")
public class AuctionItem {
    @Id
    private String auctionItemId;

    private Double reservePrice = 0.00;
    private Double currentBid = 0.00;
    private String currentBidder;
    private Item item;

    public AuctionItem(Double reservePrice, Item item) {
        this.reservePrice = reservePrice;
        this.item = item;
    }

    /**
     * @return the auctionItemId
     */
    public String getAuctionItemId() {
        return auctionItemId;
    }

    /**
     * @param auctionItemId the auctionItemId to set
     */
    public void setAuctionItemId(String auctionItemId) {
        this.auctionItemId = auctionItemId;
    }

    /**
     * @return the reservePrice
     */
    public Double getReservePrice() {
        return reservePrice;
    }

    /**
     * @param reservePrice the reservePrice to set
     */
    public void setReservePrice(Double reservePrice) {
        this.reservePrice = reservePrice;
    }

    /**
     * @return the item
     */
    public Item getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * @return the currentBid
     */
    public Double getCurrentBid() {
        return currentBid;
    }

    /**
     * @param currentBid the currentBiid to set
     */
    public void setCurrentBid(Double currentBid) {
        this.currentBid = currentBid;
    }

    /**
     * @return the currentBidder
     */
    public String getCurrentBidder() {
        return currentBidder;
    }

    /**
     * @param currentBidder the currentBidder to set
     */
    public void setCurrentBidder(String currentBidder) {
        this.currentBidder = currentBidder;
    }

}