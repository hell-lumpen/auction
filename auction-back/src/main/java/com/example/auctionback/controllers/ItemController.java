package com.example.auctionback.controllers;

import com.example.auctionback.controllers.models.ItemRequest;
import com.example.auctionback.controllers.models.ItemResponse;
import com.example.auctionback.controllers.models.SlaveRequest;
import com.example.auctionback.controllers.models.SlaveResponse;
import com.example.auctionback.exceptions.ItemNotFoundException;
import com.example.auctionback.exceptions.SlaveNotExistException;
import com.example.auctionback.services.ItemService;
import com.example.auctionback.services.SlaveService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
@AllArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/{id}")
    public ItemResponse getSlave(@PathVariable("id") Long itemId) throws ItemNotFoundException {
        //todo: get all items
        return itemService.getItemFromDB(itemId);
    }

    @GetMapping("/get_all")
    public List<ItemResponse> getSlaves()  {
        return itemService.getAllItemFromDB();
    }

    @PostMapping("/new_item")
    public ItemResponse slaveRegistration(@RequestBody ItemRequest itemRequest) {

        return itemService.saveItemInDB(itemRequest);
    }
}
