package com.example.auctionback.models;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {
    private String id;
    private String name;
    private String description;
    private String ownerId;
    private String auctionId;
}
