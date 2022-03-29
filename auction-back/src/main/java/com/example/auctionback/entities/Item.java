package com.example.auctionback.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Setter
@Getter
@AllArgsConstructor
public class Item {
    private long id;
    private String name;
    private String description;
    private long ownerId;
    private long auctionId;
//    private Image icon;

}
