package com.auctioncorp.auctionapp.model;

/* This class has been added to support the return of only an item id for the /auctionitems post endpoint. 
   While this can be done in several ways, I prefer to have all of my possible JSON objects to correspond 
   to classes. */
public class AuctionItemIdDTO {
    private String auctionItemId;

    public AuctionItemIdDTO(String auctionItemId) {
        this.auctionItemId = auctionItemId;
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

}