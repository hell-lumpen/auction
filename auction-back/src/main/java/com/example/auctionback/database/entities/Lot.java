package com.example.auctionback.database.entities;

import lombok.*;
import org.springframework.core.io.ResourceLoader;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;



//    private Long itemId;
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "lot")
    private List<Order> orders;
//    private Long lastOrderId;

    private String startCost;
    private String minBidIncrease;


    private String ownerNickname;

    private boolean lotStatus = false;

    private LocalDateTime createAt;
    private LocalDateTime finishAt;
}
