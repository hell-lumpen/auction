package com.example.auctionback.database.repository;

import com.example.auctionback.database.entities.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    Optional<List<Item>> findByOwnerNickname(String bidderNickname);
}
