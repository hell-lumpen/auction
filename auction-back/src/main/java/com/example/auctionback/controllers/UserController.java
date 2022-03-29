package com.example.auctionback.controllers;

import com.example.auctionback.entities.*;
import com.example.auctionback.models.UserRequest;
import com.example.auctionback.models.ItemRequest;
import com.example.auctionback.exceptions.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserController{

    public static User getUser(String userId, List<User> users) throws UserNotExistException {
        if (users == null)
            throw new UserNotExistException();

        return findUser(userId, users);
    }

    public static List<User> getUsers(List<User> users) {
        return users;
    }

    public static User postUser(@RequestBody UserRequest userRequest, List<User> users) throws UserExistException {
        if (users != null) {
            for (User user : users) {
                if (user.getUserId() == userRequest.getUserId())
                    throw new UserExistException();
            }
        }

        var user = new User();
        user.setUserId(userRequest.getUserId());
        user.setName(userRequest.getName());
        user.setItems(userRequest.getItems());
        assert users != null;
        users.add(user);
        return user;
    }

    public static List<Item> getUserItems(String userId, List<User> users)throws UserNotExistException {
        if (users == null)
            throw new UserNotExistException();

        return findUser(userId, users).getItems();
    }

    public static Item getUserItem(String userId, String itemId,
                                   List<User> users)throws ItemNotExistException, UserNotExistException{
        if (users == null)
            throw new UserNotExistException();

        var user = findUser(userId, users);
        if (user.getItems() == null)
            throw new ItemNotExistException();

        for (Item item : user.getItems()) {
            if (item.getId() == Integer.parseInt(itemId)){
                return item;
            }
        }
        throw new ItemNotExistException();

    }

    public static Item postItem(@PathVariable("id") String userId,
                                @RequestBody ItemRequest itemRequest,
                                List<User> users)throws ItemExistException, UserNotExistException {

        var user = findUser(userId, users);
        if (user.getItems() != null)
            for (Item item : user.getItems())
                if (item.getId() == itemRequest.getId())
                    throw new ItemExistException();


        var item = new Item(itemRequest.getId(), itemRequest.getName(), itemRequest.getDescription(),
                Long.parseLong(userId), itemRequest.getAuctionId());

        user.addItem(item);
        return item;
    }

    private static User findUser(String userId, List<User> users) throws UserNotExistException{
        for (User user:
                users)
            if (user.getUserId() == Long.parseLong(userId))
                return user;

        throw new UserNotExistException();
    }
}