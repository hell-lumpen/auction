package com.example.auctionback.controllers;


import com.example.auctionback.database.entities.Auction;
import com.example.auctionback.models.AuctionRequest;
import com.example.auctionback.models.ItemRequest;
import com.example.auctionback.models.ItemResponse;
import com.example.auctionback.models.UserRequest;
import com.example.auctionback.database.entities.Application;
import com.example.auctionback.database.entities.Item;
import com.example.auctionback.database.entities.User;
import com.example.auctionback.controllers.exceptions.*;
import com.example.auctionback.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/auction")
public class ApplicationController {

    private final ItemService itemService;

    public ApplicationController(ItemService itemService) {
        this.itemService = itemService;
    }

    private final float MIN_BID_INCREASE = 50f;
    private static final Application app = new Application();

    @GetMapping("/all")
    public List<Auction> getsAuctions(){
        return app.getAuctions();
    }

    @GetMapping("/{id}")
    public Auction getsAuction(@PathVariable("id") String auctionId) throws AuctionNotExistException{

        for (var auction : app.getAuctions()) {
            if (Objects.equals(auction.getAuctionId(), auctionId)) {
                return auction;
            }
        }

        throw new AuctionNotExistException();
    }

    @PostMapping("/user/{id}/items/{id_item}/choose")  // http://localhost:8080/create_auction
    public Auction postAuction(@PathVariable("id") String userId,
                               @PathVariable("id_item") String itemId,
                               @RequestBody AuctionRequest auctionRequest)
            throws AuctionExistException, ItemExistException, UserNotExistException {

        // TODO: добавить проверку на существование таких userId и itemId

        for (var auction : app.getAuctions()) {

            if (Objects.equals(auction.getAuctionId(), auctionRequest.getId())) {
                throw new AuctionExistException();
            }

            if (Objects.equals(auction.getItemId(), itemId)) {
                throw new AuctionExistException();
            }
        }

        Auction auction = new Auction(auctionRequest.getId(),
                itemId,
                auctionRequest.getTimeAuction(),
                auctionRequest.getCurrentBidCost(),
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
        return app.getUsers();
    }

    @PostMapping("/user/{id}/items")
    public ItemResponse postItem(@PathVariable("id") String userId,
                                 @RequestBody ItemRequest itemRequest) throws ItemExistException, UserNotExistException {
        return itemService.saveItem(itemRequest);
    }

    @PostMapping("/user")
    public User postUser(@RequestBody UserRequest userRequest) throws UserExistException {
        return UserController.postUser(userRequest, app.Users);
    }

}
