package com.example.auctionback.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Auction Already exist")  // 409
public class AuctionExistException extends Exception{

}