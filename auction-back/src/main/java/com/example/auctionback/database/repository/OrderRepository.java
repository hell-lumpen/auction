package com.example.auctionback.database.repository;

import com.example.auctionback.controllers.models.OrderDTO;
import com.example.auctionback.database.entities.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Order findByOrderId(Long lastOrderId);
}
