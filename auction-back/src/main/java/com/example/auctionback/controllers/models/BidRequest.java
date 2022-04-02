package com.example.auctionback.controllers.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidRequest {
    private Long auctionId;
    private float currentBid;
    private Long ownerId;
}
