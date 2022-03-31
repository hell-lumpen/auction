package com.example.auctionback.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Application {

    public List<Auction> Auctions = new ArrayList<Auction>();
    public List<User> Users = new ArrayList<User>();

    public static class Transfer {
        public String reverseMoney() {
            return null;
        }

        public String transferMoney() {
            return null;
        }
    }
}
