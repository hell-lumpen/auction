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
    private String time_auction;

    private float current_bid_cost;
    private float bid_min_increase;

    private String current_item_owner_id;
    private String auction_initiator_id;
}