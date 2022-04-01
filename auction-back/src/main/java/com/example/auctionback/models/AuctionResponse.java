package com.example.auctionback.models;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuctionResponse {
    private String id;
    private String itemId;
    private String timeAuction;

    private float currentBidCost;
    private float bidMinIncrease;

    private String currentItemOwnerId;
}
