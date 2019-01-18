package com.auctioncorp.auctionapp.model;

public class User {
    private String userId;
    private String userDisplayName;

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the userDisplayName
     */
    public String getUserDisplayName() {
        return userDisplayName;
    }

    /**
     * @param userDisplayName the userDisplayName to set
     */
    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }

}