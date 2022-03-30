package com.example.auctionback.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Auction {
    private String id;
    private String itemId;
    private String time_auction;

    private float current_bid_cost;
    private float bid_min_increase = 50;

    private String current_item_owner_id;
    private String auction_initiator_id;

//    private Data start_date;
//    private Data expired_date;

}
