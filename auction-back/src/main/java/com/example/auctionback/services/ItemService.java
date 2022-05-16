package com.example.auctionback.services;

import com.example.auctionback.database.entities.Item;
import com.example.auctionback.exceptions.DataNotCorrectException;
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

    public ItemDTO getItem(Long itemId, OurAuthToken token) throws ItemNotFoundException, DataNotCorrectException {

        Optional<Item> existedItem = itemRepository.findById(itemId);
        Item item = existedItem.orElseThrow(ItemNotFoundException::new);
//        todo: исправить маппер и добавить проверку на текущего юзера
        if (!token.getPrincipal().getNickname().equals(item.getOwnerNickname())) {
            throw new DataNotCorrectException();
        }

        return ItemDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .ownerNickname(item.getOwnerNickname())
                .build();
    }

    public ItemDTO saveItem(ItemDTO itemDTO, OurAuthToken token) {
        itemDTO.setOwnerNickname(token.getPrincipal().getNickname());
        Item item = mapper.map(itemDTO, Item.class);
        itemRepository.save(item);
        itemDTO.setId(item.getId());
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
