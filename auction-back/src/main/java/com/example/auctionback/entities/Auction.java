package com.example.auctionback.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long itemId;
    private float bidCost;
    private float minBidIncrease;
    private Long bidOwnerId;

    public Auction(String name, Long itemId, float bidCost, float minBidIncrease, Long bidOwnerId) {
        this.name = name;
        this.itemId = itemId;
        this.bidCost = bidCost;
        this.minBidIncrease = minBidIncrease;
        this.bidOwnerId = bidOwnerId;
    }

}
