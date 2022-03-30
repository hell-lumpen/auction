package com.example.auctionback.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Setter
@Getter
@AllArgsConstructor
public class Item {
    private String id;
    private String name;
    private String description;
    private String ownerId;
    private String auctionId;
//    private Image icon;

}
