package com.example.auctionback.services.impl;

import com.example.auctionback.controllers.models.ItemDTO;
import com.example.auctionback.controllers.models.BidderDTO;
import com.example.auctionback.database.entities.Item;
import com.example.auctionback.database.entities.Bidder;
import com.example.auctionback.exceptions.DataNotCorrectException;
import com.example.auctionback.exceptions.ItemAlreadyExistException;
import com.example.auctionback.exceptions.ItemNotFoundException;
import com.example.auctionback.exceptions.BidderNotFoundException;
import com.example.auctionback.database.repository.ItemRepository;
import com.example.auctionback.database.repository.BidderRepository;
import com.example.auctionback.security.models.OurAuthToken;
import com.example.auctionback.services.BidderService;
import lombok.Data;
import org.modelmapper.ModelMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BidderServiceImpl implements BidderService {
    private final ModelMapper mapper;
    private final BidderRepository bidderRepository;
    private final ItemRepository itemRepository;


    public BidderDTO getBidder(String bidderNickname, OurAuthToken token)
            throws BidderNotFoundException, DataNotCorrectException {

        Optional<Bidder> existedBidder = bidderRepository.findOptionalByNickname(bidderNickname);
        Bidder bidder = existedBidder.orElseThrow(BidderNotFoundException::new); // по идее нахер не нужно ибо такая ошибка тут невозможна
        if (bidder.getNickname() != token.getPrincipal().getNickname())
            throw new DataNotCorrectException();

        return BidderDTO.builder()
                .id(bidder.getId())
                .name(bidder.getName())
                .money(bidder.getMoney())
                .reservedMoney(bidder.getReservedMoney())
                .nickname(bidder.getNickname())
                .password(bidder.getPassword())
                .build();
    }

    public List<BidderDTO> getAllBidders() {
        List<Bidder> allBidders = (List<Bidder>) bidderRepository.findAll();

        return  allBidders.stream()
                .map(bidder -> BidderDTO.builder()
                        .id(bidder.getId())
                        .name(bidder.getName())
                        .money(bidder.getMoney())
                        .reservedMoney(bidder.getReservedMoney())
                        .nickname(bidder.getNickname())
                        .password(bidder.getPassword())
                        .build()
                ).collect(Collectors.toList());
    }

    public List<ItemDTO> getAllBidderItems(String bidderNickname)
            throws ItemNotFoundException, BidderNotFoundException {

        bidderRepository.findOptionalByNickname(bidderNickname).orElseThrow(BidderNotFoundException::new);

        Optional<List<Item>> items = itemRepository.findByOwnerNickname(bidderNickname);
        List<Item> allBidderItems = items.orElseThrow(ItemNotFoundException::new);
        return  allBidderItems.stream()
                .map(item -> ItemDTO.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .description(item.getDescription())
                        .ownerNickname(item.getOwnerNickname())
                        .build()
                ).collect(Collectors.toList());
    }

    public ItemDTO addItem(String bidderNickname, ItemDTO itemRequest)
            throws ItemAlreadyExistException, BidderNotFoundException {

        bidderRepository.findOptionalByNickname(bidderNickname).
                orElseThrow(BidderNotFoundException::new);//need to throw exception
        Item item = mapper.map(itemRequest, Item.class);
        itemRepository.save(item);
        return itemRequest;
    }

    public BidderDTO deleteBidder(String bidderNickname) throws BidderNotFoundException {
        Bidder bidder = bidderRepository.findOptionalByNickname(bidderNickname).orElseThrow(BidderNotFoundException::new);
        bidderRepository.delete(bidder);
        return mapper.map(bidder, BidderDTO.class);
    }
}
