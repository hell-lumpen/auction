package com.example.auctionback.database.repositories;

import com.example.auctionback.database.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {

    Optional<Item> findOptionalId(String id);

    boolean existsById(String id);
}
