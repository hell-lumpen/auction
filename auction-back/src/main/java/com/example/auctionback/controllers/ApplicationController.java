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

@RestController
@RequestMapping("/auction")
public class ApplicationController {
    private static Application app = new Application();

    @GetMapping("/all")
    public List<Auction> getsAuctions(){
        return app.getAuctions();
    }

    @GetMapping("/{id}")
    public Auction getsAuction(@PathVariable("id") String auctionId)throws AuctionNotExistException{
        for (var f : app.getAuctions())
            if (f.getId() == Long.parseLong(auctionId))
                return f;

        throw new AuctionNotExistException();
    }

    @PostMapping("/user/{id}/items/{id_item}/choose")
    public Auction postAuction(@PathVariable("id") String userId,
                            @PathVariable("id_item") String itemId,
                            @RequestBody AuctionRequest auctionRequest)
            throws AuctionExistException, ItemExistException, UserNotExistException {

        for (var f : app.getAuctions()){
            if (f.getId() == auctionRequest.getId())
                throw new AuctionExistException();

            if (f.getItemId() == auctionRequest.getItemId())
                throw new AuctionExistException();
        }

        Auction auc = new Auction();
        auc.setId(auctionRequest.getId());
        auc.setItemId(Long.parseLong(itemId));
        auc.setTime_auction(auctionRequest.getTime_auction());
        auc.setCurrent_bid_cost(auctionRequest.getCurrent_bid_cost());
        auc.setCurrent_item_owner_id(Long.parseLong(userId));
        auc.setAuction_initiator_id(Long.parseLong(userId));
        app.Auctions.add(auc);
        return auc;
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
