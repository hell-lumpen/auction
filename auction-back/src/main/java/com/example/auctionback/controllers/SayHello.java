package com.example.auctionback.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class SayHello {

    @GetMapping()
    public String say_hello() {
        return "Hello world";
    }
}