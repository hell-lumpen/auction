package com.example.auctionback.repository;

import com.example.auctionback.entities.Auction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuctionRepository extends CrudRepository<Auction, Long> {
    Optional<Long> findByItemId(Long id);
    Optional<Long> findByBidOwnerId(Long id);
    Boolean existsByItemId(Long id);
}
