package com.example.auctionback.controllers.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LotDTO {
    private long id;

    private String title;
    private long itemId;

    private float bidCost;
    private float minBidIncrease;

    private long bidOwnerId;
}
