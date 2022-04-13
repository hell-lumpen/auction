package com.example.auctionback.database.repository;

import com.example.auctionback.database.entities.Lot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LotRepository extends CrudRepository<Lot, Long>, PagingAndSortingRepository<Lot, Long> {
    Optional<Long> findByItemId(Long id);
    Optional<Long> findByOwnerId(Long id);
    Boolean existsByItemId(Long id);
}
