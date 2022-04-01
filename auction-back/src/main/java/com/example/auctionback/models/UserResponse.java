package com.example.auctionback.models;

import com.example.auctionback.database.entities.Item;
import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String userId;
    private String name;
    private List<Item> items;
}
