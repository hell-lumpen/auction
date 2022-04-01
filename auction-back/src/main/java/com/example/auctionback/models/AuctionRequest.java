package com.example.auctionback.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuctionRequest {
    private String id;
    private String itemId;
    private String timeAuction;

    private float currentBidCost;
    private float bidMinIncrease;

    private String currentItemOwnerId;
}