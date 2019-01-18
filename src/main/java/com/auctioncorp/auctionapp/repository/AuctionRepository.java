package com.auctioncorp.auctionapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auctioncorp.auctionapp.model.AuctionItem;

@Repository
public interface AuctionRepository extends CrudRepository<AuctionItem, String> {
}