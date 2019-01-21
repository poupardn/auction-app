package com.auctioncorp.auctionapp.controller;

import com.auctioncorp.auctionapp.AuctionApplication;
import com.auctioncorp.auctionapp.model.AuctionItem;
import com.auctioncorp.auctionapp.model.Bid;
import com.auctioncorp.auctionapp.model.Item;
import org.apache.http.HttpStatus;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import com.auctioncorp.auctionapp.repository.AuctionRepository;
import com.auctioncorp.auctionapp.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.json.JSONException;
import org.json.JSONObject;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuctionApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuctionControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    AuctionRepository auctionRepository;

    @Before
    public void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    public void addAuction() {
        try {
            JSONObject jsonObj = new JSONObject().put("reservePrice", 10450.00).put("item",
                    new JSONObject().put("itemid", "abcd").put("description", "item description"));
            String str = jsonObj.toString();
            given().contentType("application/json").body(jsonObj.toString()).when().contentType("application/json")
                    .post("/auctionitems").then().assertThat().body("auctionItemId", is(not(nullValue())));

        } catch (JSONException jse) {
            throw new RuntimeException("Json fail");
        }
    }
}