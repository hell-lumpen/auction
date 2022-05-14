package com.example.auctionback.services;

import com.example.auctionback.database.entities.Item;
import com.example.auctionback.exceptions.ItemNotFoundException;
import com.example.auctionback.database.repository.ItemRepository;
import com.example.auctionback.controllers.models.ItemDTO;
import com.example.auctionback.security.models.OurAuthToken;
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
//        todo: исправить маппер и добавить проверку на текущего юзера
        return mapper.map(item, ItemDTO.class);
    }

    public ItemDTO saveItem(ItemDTO itemDTO, OurAuthToken token) {
        Item item = mapper.map(itemDTO, Item.class);
        item.setOwnerNickname(token.getPrincipal().getNickname());
        itemRepository.save(item);
        return itemDTO;
    }

    public List<ItemDTO> getAllItem(OurAuthToken token) {
        List<Item> allItems = itemRepository.findByOwnerNickname(token.getPrincipal().getNickname()).orElseThrow();

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
