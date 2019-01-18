package com.auctioncorp.auctionapp.model;

public class Bid {
    private String auctionItemId;
    private Double maxAutoBidAmount;
    private String bidderName;

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
    public String getBidder() {
        return bidderName;
    }

    /**
     * @param bidderName the bidderName to set
     */
    public void setBidder(String bidderName) {
        this.bidderName = bidderName;
    }

}