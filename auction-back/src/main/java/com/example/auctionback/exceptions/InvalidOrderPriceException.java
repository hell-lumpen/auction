package com.example.auctionback.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,
        reason = "Invalid order price (the price cannot be lower than the previous value, taking into account the minimum price increase)")
public class InvalidOrderPriceException extends Exception {
}
