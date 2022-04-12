package com.example.auctionback.controllers.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDTO {
    private Long orderId;

    private Long orderOwnerId;
    private String orderPrice;
    private Long itemId;
    private Long auctionId;

    private Date createdAt;
    private boolean orderStatus;
}
