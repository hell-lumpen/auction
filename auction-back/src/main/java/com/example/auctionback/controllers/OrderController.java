package com.example.auctionback.controllers;

import com.example.auctionback.controllers.models.OrderDTO;
import com.example.auctionback.database.entities.Order;
import com.example.auctionback.services.OrderService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/private")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

//    @PostMapping("/orders")
//    public OrderDTO createNewOrder(@RequestBody OrderDTO orderRequest) {
//        return orderService.createNewOrder(orderRequest);
//    }

    @GetMapping("/orders/{id}")
    public OrderDTO getOrder(@PathVariable("id") Long orderId) {
        return orderService.getOrder(orderId);
    }

    @PutMapping("/orders/{id}")
    public OrderDTO updateOrder(@PathVariable("id") Long orderId) {
        return orderService.updateOrder(orderId);
    }

    @DeleteMapping("/orders/{id}")
    public OrderDTO deleteOrder(@PathVariable("id") Long orderId) {
        return orderService.deleteOrder(orderId);
    }

    private OrderDTO convertToDTO(Order order) {
        return new ModelMapper().map(order, OrderDTO.class);
    }

    private Order convertToEntity(OrderDTO orderDTO) {
        return new ModelMapper().map(orderDTO, Order.class);
    }
}
