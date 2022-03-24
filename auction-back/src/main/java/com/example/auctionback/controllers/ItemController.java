package com.example.auctionback.controllers;

import com.example.auctionback.entities.Item;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user/{id}")
public class ItemController {
    @GetMapping("/getItems")
    String getItems(long ownerId, @PathVariable String id) {
        return null;
    }
    @PostMapping("/postItem")
    String postItem(Item item, @PathVariable String id) {
        return null;
    }
}
