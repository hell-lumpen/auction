package com.example.auctionback.controllers.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LotDTO {
    private Long id;
    private String title;
    private String description;
    private Long itemId;
    private String currentCost;
    private String minBidIncrease;

    private Long ownerId;

}
