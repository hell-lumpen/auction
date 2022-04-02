package com.example.auctionback.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Slave")
public class Slave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private float money;
    private float reservedMoney;
    private String password;

    public Slave(String name, float money, float reversedMoney, String password) {
        this.name = name;
        this.money = money;
        this.password = password;
        this.reservedMoney = reversedMoney;
    }
}
