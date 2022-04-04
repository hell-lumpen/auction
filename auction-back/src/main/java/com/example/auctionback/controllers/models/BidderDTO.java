package com.example.auctionback.controllers.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BidderDTO {
    private Long id;

    private String name;
    private float money;
    private float reservedMoney;

    private String nickname;
    private String password;
}
