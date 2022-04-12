package com.example.auctionback.database.entities;

import lombok.*;
import org.springframework.core.io.ResourceLoader;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private Long itemId;
    private Long lastOrderId;
    private String minBidIncrease;

    private Long ownerId;

    private boolean lotStatus = false;

    private Date createAt;
    private Date finishAt = null;
}