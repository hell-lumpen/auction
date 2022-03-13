package com.example.auctionback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class AuctionBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuctionBackApplication.class, args);
    }

}