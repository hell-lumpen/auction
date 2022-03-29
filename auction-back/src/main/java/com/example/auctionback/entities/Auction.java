package com.example.auctionback.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Auction {
    private long id;
    private Item item;

    private float current_bid_cost;
    private float previous_bid_cost;
    private float bid_min_increase;

    private long current_item_owner_id;
    private long auction_initiator_id;

//    private Data start_date;
//    private Data expired_date;

}
