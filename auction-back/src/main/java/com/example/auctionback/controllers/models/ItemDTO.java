package com.example.auctionback.controllers.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDTO {
    private Long id;

    private String name;
    private String description;

    private String ownerNickname;
}
