package com.example.auctionback.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Item isn't exist")  // 404
public class ItemNotExistException extends Exception{

}