package com.auctioncorp.auctionapp.repository;

import com.auctioncorp.auctionapp.model.Bid;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends CrudRepository<Bid, String> {
    List<Bid> findByAuctionItemId(String auctionItemId);

}