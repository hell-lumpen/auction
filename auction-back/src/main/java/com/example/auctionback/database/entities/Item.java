package com.example.auctionback.database.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Item {
    @Id
    private String itemId;
    private String name;
    private String description;
    private String ownerId;
    private String auctionId;
//    private Image icon;

}
