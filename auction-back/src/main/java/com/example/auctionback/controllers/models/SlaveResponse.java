package com.example.auctionback.controllers.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlaveResponse {
    private long id;
    private String name;
    private float money;
    private float reversedMoney;
    private String password;
}
