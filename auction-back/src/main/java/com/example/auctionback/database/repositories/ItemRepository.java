package com.example.auctionback.database.repositories;

import com.example.auctionback.database.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Repository
public interface ItemRepository extends JpaRepository<Item, String> {

    boolean existsById(String id);
}
