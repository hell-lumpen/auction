package com.example.auctionback.models;

import com.example.auctionback.entities.Item;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuctionRequest {
    private String id;
    private String itemId;
    private String timeAuction;

    private float currentBidCost;
    private float bidMinIncrease;

    private String currentItemOwnerId;
    private String auctionInitiatorId;
}