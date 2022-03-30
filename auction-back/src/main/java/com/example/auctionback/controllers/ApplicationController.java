package com.example.auctionback.controllers;


import com.example.auctionback.entities.Auction;
import com.example.auctionback.models.AuctionRequest;
import com.example.auctionback.models.ItemRequest;
import com.example.auctionback.models.UserRequest;
import com.example.auctionback.entities.Application;
import com.example.auctionback.entities.Item;
import com.example.auctionback.entities.User;
import com.example.auctionback.exceptions.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/auction")
public class ApplicationController {

    private final float MIN_BID_INCREASE = 50f;
    private static final Application app = new Application();

    @GetMapping("/all")
    public List<Auction> getsAuctions(){
        return app.getAuctions();
    }

    @GetMapping("/{id}")
    public Auction getsAuction(@PathVariable("id") String auctionId)throws AuctionNotExistException{
        for (var auction : app.getAuctions())
            if (Objects.equals(auction.getId(), auctionId))
                return auction;

        throw new AuctionNotExistException();
    }

    @PostMapping("/user/{id}/items/{id_item}/choose")
    public Auction postAuction(@PathVariable("id") String userId,
                            @PathVariable("id_item") String itemId,
                            @RequestBody AuctionRequest auctionRequest)
            throws AuctionExistException, ItemExistException, UserNotExistException {

        for (var auction : app.getAuctions()){
            if (Objects.equals(auction.getId(), auctionRequest.getId()))
                throw new AuctionExistException();

            if (Objects.equals(auction.getItemId(), auctionRequest.getItemId()))
                throw new AuctionExistException();
        }

        Auction auction = new Auction(auctionRequest.getId(),
                itemId,
                auctionRequest.getTime_auction(),
                auctionRequest.getCurrent_bid_cost(),
                MIN_BID_INCREASE,
                userId,
                userId);

        app.Auctions.add(auction);
        return auction;
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") String userId) throws UserNotExistException {
        return UserController.getUser(userId, app.Users);
    }

    @GetMapping("/user/{id}/items")
    public List<Item> getUserItems(@PathVariable("id") String userId)
            throws UserNotExistException {
        return UserController.getUserItems(userId, app.Users);
    }

    @GetMapping("/user/{id}/items/{id_item}")
    public Item getUserItem(@PathVariable("id") String userId,
                            @PathVariable("id_item") String itemId
                            ) throws ItemNotExistException, UserNotExistException {
        return UserController.getUserItem(userId, itemId, app.Users);
    }

    @GetMapping("/user/get_users")
    public List<User> getUsers() {
        return UserController.getUsers(app.getUsers());
    }

    @PostMapping("/user/{id}/items")
    public Item postItem(@PathVariable("id") String userId,
                         @RequestBody ItemRequest itemRequest) throws ItemExistException, UserNotExistException {
        return UserController.postItem(userId, itemRequest, app.Users);
    }

    @PostMapping("/user")
    public User postUser(@RequestBody UserRequest userRequest) throws UserExistException {
        return UserController.postUser(userRequest, app.Users);
    }

}
