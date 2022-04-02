package com.example.auctionback.controllers.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuctionResponse {
    private long id;
    private String name;
    private long itemId;
    private int bidCoast;
    private int bidMinIncrease;
    private long bidOwnerId;
}
