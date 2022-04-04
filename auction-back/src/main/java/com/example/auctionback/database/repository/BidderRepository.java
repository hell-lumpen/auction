package com.example.auctionback.database.repository;


import com.example.auctionback.database.entities.Bidder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BidderRepository extends CrudRepository<Bidder, Long> {
}

