package com.example.auctionback.controllers.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDTO {
    private long id;

    private String name;
    private String description;

    private long ownerId;

    String ge(String m){
        String name = "a";
        return "my full name is " + name + " oh yeah";
    }
}
