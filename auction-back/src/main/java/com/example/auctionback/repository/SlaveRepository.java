package com.example.auctionback.repository;


import com.example.auctionback.controllers.models.ItemResponse;
import com.example.auctionback.entities.Item;
import com.example.auctionback.entities.Slave;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SlaveRepository extends CrudRepository<Slave, Long> {
}

