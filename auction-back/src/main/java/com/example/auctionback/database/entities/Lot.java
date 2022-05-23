package com.example.auctionback.database.entities;

import lombok.*;
import org.springframework.core.io.ResourceLoader;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private Long itemId;
    private Long lastOrderId;
    private String startCost;
    private String minBidIncrease;

    ;
    private String ownerNickname;

    private boolean lotStatus = false;

    private LocalDateTime createAt;
    private LocalDateTime finishAt;
}
