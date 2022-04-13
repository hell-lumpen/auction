package com.example.auctionback.database.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "auction_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Long orderOwnerId;
    private String orderPrice;
    private Long itemId;
    private Long auctionId;

    private Date createdAt;
    private boolean orderStatus; // true - текущий ордер или нет

    public boolean getOrderStatus() {
        return orderStatus;
    }
}
