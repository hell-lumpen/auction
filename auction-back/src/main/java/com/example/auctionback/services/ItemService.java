package com.example.auctionback.services;

import com.example.auctionback.controllers.models.SlaveResponse;
import com.example.auctionback.entities.Item;
import com.example.auctionback.exceptions.ItemNotFoundException;
import com.example.auctionback.repository.ItemRepository;
import com.example.auctionback.controllers.models.ItemResponse;
import com.example.auctionback.controllers.models.ItemRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemResponse getItemFromDB(Long itemId) throws ItemNotFoundException {
        Optional<Item> existedItem = itemRepository.findById(itemId);
        Item slave = existedItem.orElseThrow(ItemNotFoundException::new); // по идее нахер не нужно ибо такая ошибка тут невозможна

        return new ItemResponse(itemId,
                slave.getName(),
                slave.getDescription(),
                slave.getOwnerId());
    }

    public ItemResponse saveItemInDB(ItemRequest itemRequest) {
        Item item = new Item(itemRequest.getName(),
                itemRequest.getDescription(),
                itemRequest.getOwnerId());

        itemRepository.save(item); // add in db

        return new ItemResponse(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getOwnerId());
    }

    public List<ItemResponse> getAllItemFromDB() {
        List<Item> allItems = (List<Item>) itemRepository.findAll();

        return  allItems.stream()
                .map(item -> new ItemResponse(
                        item.getId(),
                        item.getName(),
                        item.getDescription(),
                        item.getOwnerId()
                )).collect(Collectors.toList());
    }
}
