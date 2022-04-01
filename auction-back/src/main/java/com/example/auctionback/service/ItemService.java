package com.example.auctionback.service;

import com.example.auctionback.controllers.exceptions.ItemExistException;
import com.example.auctionback.database.entities.Item;
import com.example.auctionback.database.repositories.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.auctionback.models.*;

import java.util.List;

@Service
@AllArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemResponse saveItem(ItemRequest itemRequest) throws ItemExistException {

        if (itemRequest.getId() != null && itemRepository.existsById(itemRequest.getId())) {
            throw new ItemExistException();
        }

        Item newItem = new Item();
        newItem.setName(itemRequest.getName());
        newItem.setDescription(itemRequest.getDescription());
        newItem.setOwnerId(itemRequest.getOwnerId());
        newItem.setAuctionId(itemRequest.getAuctionId());
        itemRepository.save(newItem);

        return new ItemResponse(
                newItem.getItemId(),
                newItem.getName(),
                newItem.getDescription(),
                newItem.getOwnerId(),
                newItem.getAuctionId()
        );
    }

}
