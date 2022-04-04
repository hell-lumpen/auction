package com.example.auctionback.controllers;

import com.example.auctionback.controllers.models.ItemDTO;
import com.example.auctionback.exceptions.ItemNotFoundException;
import com.example.auctionback.services.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
@AllArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/{id}")
    public ItemDTO getSlave(@PathVariable("id") Long itemId) throws ItemNotFoundException {
        //todo: get all items
        return itemService.getItem(itemId);
    }

    @GetMapping("/all")
    public List<ItemDTO> getSlaves()  {
        return itemService.getAllItem();
    }

    @PostMapping("")
    public ItemDTO slaveRegistration(@RequestBody ItemDTO itemRequest) {

        return itemService.saveItem(itemRequest);
    }
}
