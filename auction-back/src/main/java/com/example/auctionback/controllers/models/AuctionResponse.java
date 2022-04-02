package com.example.auctionback.controllers.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuctionResponse {

    private Long id;
    private String name;
    private Long itemId;
    private float bidCost;
    private float minBidIncrease;
    private Long bidOwnerId;

    public AuctionResponse(String name, Long itemId, float bidCost, float minBidIncrease, Long bidOwnerId) {
        this.name = name;
        this.itemId = itemId;
        this.bidCost = bidCost;
        this.minBidIncrease = minBidIncrease;
        this.bidOwnerId = bidOwnerId;
    }
}
