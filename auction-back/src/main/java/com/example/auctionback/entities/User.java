package com.example.auctionback.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private String userId;
    private String name;
    private List<Item> items;
    //    private Image icon;
    private static class Account {
        private long ownerId;
        private float currentMoney;
        private float reservedMoney;
    }

    public void addItem(Item item) {
        if (this.items == null){
            this.items = new ArrayList<>(List.of(item)){};
        }
        else
            this.items.add(item);
    }

    private void deleteItem(long id){

    }


}
