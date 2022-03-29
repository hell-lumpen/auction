package com.example.auctionback.models;

import com.example.auctionback.entities.Item;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemRequest {
    private long id;
    private String name;
    private String description;
    private long ownerId;
    private long auctionId;
}