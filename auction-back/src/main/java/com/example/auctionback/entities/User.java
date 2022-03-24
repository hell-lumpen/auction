package com.example.auctionback.entities;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class User {
    private long userId;
    private String name;
    private Image icon;

    private static class Account {
        private long ownerId;
        private double currentMoney;
        private double reservedMoney;
    }
}
