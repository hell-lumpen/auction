package com.example.auctionback.repository;

import com.example.auctionback.entities.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    Optional<List<Item>> findByOwnerId(Long slaveId);
}
