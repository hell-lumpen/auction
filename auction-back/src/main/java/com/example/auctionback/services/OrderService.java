package com.example.auctionback.services;

import com.example.auctionback.controllers.models.OrderDTO;
import com.example.auctionback.database.repository.LotRepository;
import com.example.auctionback.database.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final LotRepository lotRepository;

    public OrderDTO createNewOrder(OrderDTO orderRequest) {
        return null;
    }

    public OrderDTO getOrder(Long orderId) {
        return null;
    }

    public OrderDTO updateOrder(Long orderId) {
        return null;
    }

    public OrderDTO deleteOrder(Long orderId) {
        return null;
    }

    //todo: delete last history of lot
}
