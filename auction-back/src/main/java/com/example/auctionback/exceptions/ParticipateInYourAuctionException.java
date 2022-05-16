package com.example.auctionback.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "You can not participate in your own auction")
public class ParticipateInYourAuctionException extends Exception{
}
