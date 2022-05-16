package com.example.auctionback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AuctionBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuctionBackApplication.class, args);
    }

}
