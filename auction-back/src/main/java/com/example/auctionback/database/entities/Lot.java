package com.example.auctionback.database.entities;

import lombok.*;

import javax.persistence.*;

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
    private Long itemId;
    private float bidCost;
    private float minBidIncrease;

    private Long bidOwnerId;
}
