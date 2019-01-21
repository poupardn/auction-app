package com.auctioncorp.auctionapp.model;

import org.springframework.data.redis.core.RedisHash;

@RedisHash("messages")
public class Message {
    public String bidderName;
    public String message;

    public Message(String bidderName, String message) {
        this.bidderName = bidderName;
        this.message = message;
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
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}