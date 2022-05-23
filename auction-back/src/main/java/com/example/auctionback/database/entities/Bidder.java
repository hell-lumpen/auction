package com.example.auctionback.database.entities;

import com.example.auctionback.controllers.models.BidderDTO;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Bidder")
public class Bidder {

    private String name;
    private String money;
    private String reservedMoney;
    @Id
    private String nickname;
    private String password;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "owner")
    private List<Item> items;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bidder bidderDTO = (Bidder) o;
        return Objects.equals(name, bidderDTO.name) && Objects.equals(money, bidderDTO.money) && Objects.equals(reservedMoney, bidderDTO.reservedMoney) && nickname.equals(bidderDTO.nickname) && password.equals(bidderDTO.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, money, reservedMoney, nickname, password);
    }
}
