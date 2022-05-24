package com.example.auctionback.database.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    private String orderOwnerNickname;
    private String orderPrice;
    private Long itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_id", nullable = false)
    private Lot lot;

//    private Long auctionId;

    private LocalDateTime createdAt;
    private boolean orderStatus; // true - текущий ордер или нет

    public boolean getOrderStatus() {
        return orderStatus;
    }
}
