package com.example.auctionback.database.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
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
