package com.example.auctionback.controllers.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BidderDTO {

    private String name;
    private String money;
    private String reservedMoney;

    private String nickname;
    private String password;

}
