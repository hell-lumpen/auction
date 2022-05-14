package com.example.auctionback.database.entities;

import com.example.auctionback.controllers.models.BidderDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Bidder")
public class Bidder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String money;
    private String reservedMoney;

    private String nickname;
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bidder bidderDTO = (Bidder) o;
        return Objects.equals(id, bidderDTO.id) && Objects.equals(name, bidderDTO.name) && Objects.equals(money, bidderDTO.money) && Objects.equals(reservedMoney, bidderDTO.reservedMoney) && nickname.equals(bidderDTO.nickname) && password.equals(bidderDTO.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, money, reservedMoney, nickname, password);
    }
}
