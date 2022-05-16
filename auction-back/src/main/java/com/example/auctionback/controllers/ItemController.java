package com.example.auctionback.controllers;

import com.example.auctionback.controllers.models.ItemDTO;
import com.example.auctionback.controllers.models.OrderDTO;
import com.example.auctionback.database.entities.Item;
import com.example.auctionback.database.entities.Order;
import com.example.auctionback.exceptions.DataNotCorrectException;
import com.example.auctionback.exceptions.ItemNotFoundException;
import com.example.auctionback.security.models.OurAuthToken;
import com.example.auctionback.services.ItemService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/private/item")
@AllArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/{id}")
    public ItemDTO getItem(@PathVariable("id") Long itemId, OurAuthToken token) throws ItemNotFoundException, DataNotCorrectException {
        return itemService.getItem(itemId, token);
    }

    @GetMapping("/all")
    public List<ItemDTO> getItems(OurAuthToken token)  {
        return itemService.getAllItem(token);
    }

    @PostMapping("")
    public ItemDTO saveNewItem(@RequestBody ItemDTO itemRequest, OurAuthToken token) {
//        todo: взять из хедера никнейм и добавить его в ItemDTO
        return itemService.saveItem(itemRequest, token);
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
