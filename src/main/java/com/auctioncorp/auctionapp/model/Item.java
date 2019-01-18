package com.auctioncorp.auctionapp.model;

public class Item {
    private String itemId;
    private String description;

    public Item(String itemId, String description) {
        this.itemId = itemId;
        this.description = description;
    }

    /**
     * @return the itemId
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * @param itemId the itemId to set
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

}