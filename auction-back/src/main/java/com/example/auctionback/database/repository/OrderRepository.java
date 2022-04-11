package com.example.auctionback.database.repository;

import com.example.auctionback.database.entities.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Boolean existsByOrderId(Long id);
    Optional<Order> findByOrderOwnerId(Long id);
    Optional<Order> findByItemId(Long id);
    Optional<Order> findByOrderStatus(Boolean isExecuted);
    List<Order> findByAuctionId(Long id);
}
