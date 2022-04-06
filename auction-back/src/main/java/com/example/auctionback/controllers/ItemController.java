package com.example.auctionback.controllers;

import com.example.auctionback.controllers.models.ItemDTO;
import com.example.auctionback.controllers.models.OrderDTO;
import com.example.auctionback.database.entities.Item;
import com.example.auctionback.database.entities.Order;
import com.example.auctionback.exceptions.ItemNotFoundException;
import com.example.auctionback.services.ItemService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/{id}")
    public ItemDTO getItem(@PathVariable("id") Long itemId) throws ItemNotFoundException {
        //todo: get all items
        return itemService.getItem(itemId);
    }

    @GetMapping("/all")
    public List<ItemDTO> getItems()  {
        return itemService.getAllItem();
    }

    @PostMapping("")
    public ItemDTO saveNewItem(@RequestBody ItemDTO itemRequest) {
        return itemService.saveItem(itemRequest);
    }

    @DeleteMapping()
    public ItemDTO deleteItem(@RequestBody ItemDTO itemRequest) {
//        todo: реализовать deleteItem
        return null;
    }

    private ItemDTO convertToDTO(Item item) {
        return new ModelMapper().map(item, ItemDTO.class);
    }

    private Item convertToEntity(ItemDTO itemDTO) {
        return new ModelMapper().map(itemDTO, Item.class);
    }
}
