package com.auctioncorp.auctionapp.repository;

import com.auctioncorp.auctionapp.model.Message;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, String> {
}