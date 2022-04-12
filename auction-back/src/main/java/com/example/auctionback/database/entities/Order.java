package com.example.auctionback.database.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Setter
@Getter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ownerId;
    private String price;
    private Long itemId;
    private Long auctionId;

    private Date createdAt;
    private boolean orderStatus; // true - текущий ордер или нет

    public boolean getOrderStatus() {
        return orderStatus;
    }
}
