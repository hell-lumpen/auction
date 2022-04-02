package com.example.auctionback.controllers.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuctionRequest {
    private long id;
    private String name;
    private long itemId;
    private int bidCost;
    private int minBidIncrease;
    private long bidOwnerId;
}
