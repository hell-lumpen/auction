package com.example.auctionback.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Item Already exist")  // 409
public class ItemExistException extends Exception{

}