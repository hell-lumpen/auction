package com.example.auctionback.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemRequest {
    private String id;
    private String name;
    private String description;
    private String ownerId;
    private String auctionId;
}