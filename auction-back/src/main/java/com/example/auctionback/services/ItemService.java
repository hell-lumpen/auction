package com.example.auctionback.services;

import com.example.auctionback.database.entities.Item;
import com.example.auctionback.exceptions.ItemNotFoundException;
import com.example.auctionback.database.repository.ItemRepository;
import com.example.auctionback.controllers.models.ItemDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemService {
    private final ModelMapper mapper;
    private final ItemRepository itemRepository;

    public ItemDTO getItem(Long itemId) throws ItemNotFoundException {

        Optional<Item> existedItem = itemRepository.findById(itemId);
        Item item = existedItem.orElseThrow(ItemNotFoundException::new);
        return mapper.map(item, ItemDTO.class);
    }

    public ItemDTO saveItem(ItemDTO itemDTO) {
        Item item = mapper.map(itemDTO, Item.class);
        itemRepository.save(item);
        return itemDTO;
    }

    public List<ItemDTO> getAllItem() {
        List<Item> allItems = (List<Item>) itemRepository.findAll();

        return  allItems.stream()
                .map(item -> ItemDTO.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .description(item.getDescription())
                        .ownerNickname(item.getOwnerNickname())
                        .build())
                .collect(Collectors.toList());
    }
}
