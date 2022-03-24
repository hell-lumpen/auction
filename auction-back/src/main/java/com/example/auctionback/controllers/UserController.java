package com.example.auctionback.controllers;


import com.example.auctionback.entities.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") String userId) {
        return null;
    }

    @GetMapping("/get_all")
    public String getUsers() {
        return null;
    }

    @PostMapping("/{id}")
    public String postUser(@PathVariable("id") String userId) {
        return null;
    }
}
