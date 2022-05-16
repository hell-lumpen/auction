package com.example.auctionback.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Not enough money to participate in the auction")
public class NotEnoughMoneyException extends Exception {

}
