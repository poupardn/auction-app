package com.auctioncorp.auctionapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("bids")
public class Bid {

    @Id
    private String auctionItemId;
    private Double maxAutoBidAmount;
    private String bidderName;
    private String bidId;

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
     * @return the maxAutoBidAmount
     */
    public Double getMaxAutoBidAmount() {
        return maxAutoBidAmount;
    }

    /**
     * @param maxAutoBidAmount the maxAutoBidAmount to set
     */
    public void setMaxAutoBidAmount(Double maxAutoBidAmount) {
        this.maxAutoBidAmount = maxAutoBidAmount;
    }

    /**
     * @return the bidderName
     */
    public String getBidderName() {
        return bidderName;
    }

    /**
     * @param bidderName the bidderName to set
     */
    public void setBidderName(String bidderName) {
        this.bidderName = bidderName;
    }

    /**
     * @return the bidId
     */
    public String getBidId() {
        return bidId;
    }

    /**
     * @param bidId the bidId to set
     */
    public void setBidId(String bidId) {
        this.bidId = bidId;
    }

}