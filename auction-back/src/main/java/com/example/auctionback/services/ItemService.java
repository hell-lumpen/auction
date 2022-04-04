package com.example.auctionback.services;

import com.example.auctionback.database.entities.Item;
import com.example.auctionback.exceptions.ItemNotFoundException;
import com.example.auctionback.database.repository.ItemRepository;
import com.example.auctionback.controllers.models.ItemDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemDTO getItem(Long itemId) throws ItemNotFoundException {

        Optional<Item> existedItem = itemRepository.findById(itemId);
        Item item = existedItem.orElseThrow(ItemNotFoundException::new); // по идее нахер не нужно ибо такая ошибка тут невозможна

        return ItemDTO.builder()
                .id(itemId)
                .name(item.getName())
                .description(item.getDescription())
                .ownerId(item.getOwnerId())
                .build();
    }

    public ItemDTO saveItem(ItemDTO itemDTO) {
        Item item = Item.builder()
                .name(itemDTO.getName())
                .description(itemDTO.getDescription())
                .ownerId(itemDTO.getOwnerId())
                .build();

        itemRepository.save(item);
        //todo: сделать из item в itemDTO
        return ItemDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .ownerId(item.getOwnerId())
                .build();
    }

    public List<ItemDTO> getAllItem() {
        List<Item> allItems = (List<Item>) itemRepository.findAll();

        return  allItems.stream()
                .map(item -> ItemDTO.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .description(item.getDescription())
                        .ownerId(item.getOwnerId())
                        .build())
                .collect(Collectors.toList());
    }
}
