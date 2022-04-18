package com.example.auctionback.services;

import com.example.auctionback.controllers.models.ItemDTO;
import com.example.auctionback.controllers.models.BidderDTO;
import com.example.auctionback.database.entities.Item;
import com.example.auctionback.database.entities.Bidder;
import com.example.auctionback.exceptions.ItemAlreadyExistException;
import com.example.auctionback.exceptions.ItemNotFoundException;
import com.example.auctionback.exceptions.BidderNotFoundException;
import com.example.auctionback.database.repository.ItemRepository;
import com.example.auctionback.database.repository.BidderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BidderService {

    private final BidderRepository bidderRepository;
    private final ItemRepository itemRepository;

    public BidderDTO saveBidder(BidderDTO bidderDTO) {
        //todo: сделать разные никнеймы
        Bidder bidder = Bidder.builder()
                .name(bidderDTO.getName())
                .money(bidderDTO.getMoney())
                .reservedMoney(bidderDTO.getReservedMoney())
                .password(bidderDTO.getPassword())
                .build();

        bidderRepository.save(bidder);
        return BidderDTO.builder()
                .id(bidder.getId())
                .name(bidder.getName())
                .money(bidder.getMoney())
                .reservedMoney(bidder.getReservedMoney())
                .password(bidder.getPassword())
                .build();
    }

    public BidderDTO getBidder(Long bidderId) throws BidderNotFoundException {

        Optional<Bidder> existedBidder = bidderRepository.findById(bidderId);
        Bidder bidder = existedBidder.orElseThrow(BidderNotFoundException::new); // по идее нахер не нужно ибо такая ошибка тут невозможна

        return BidderDTO.builder()
                .name(bidder.getName())
                .money(bidder.getMoney())
                .reservedMoney(bidder.getReservedMoney())
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
                    .password(bidder.getPassword())
                    .build()
                ).collect(Collectors.toList());
    }

    public List<ItemDTO> getAllBidderItems(Long bidderId)
                                                    throws ItemNotFoundException, BidderNotFoundException {

        bidderRepository.findById(bidderId).orElseThrow(BidderNotFoundException::new);

        Optional<List<Item>> items = itemRepository.findByOwnerId(bidderId);
        List<Item> allBidderItems = items.orElseThrow(ItemNotFoundException::new);
        return  allBidderItems.stream()
                .map(item -> ItemDTO.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .description(item.getDescription())
                        .ownerId(item.getOwnerId())
                        .build()
                ).collect(Collectors.toList());
    }

    public ItemDTO addItem(Long bidderId, ItemDTO itemRequest)
                                                    throws ItemAlreadyExistException, BidderNotFoundException {

        bidderRepository.findById(bidderId).orElseThrow(BidderNotFoundException::new);

        Item item = Item.builder()
                .name(itemRequest.getName())
                .description(itemRequest.getDescription())
                .ownerId(bidderId)
                .build();

        itemRepository.save(item);

        return ItemDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .ownerId(item.getOwnerId())
                .build();
    }

    public BidderDTO deleteBidder(Long bidderId) throws BidderNotFoundException {
        Bidder bidder = bidderRepository.findById(bidderId).orElseThrow(BidderNotFoundException::new);
        bidderRepository.delete(bidder);
        return BidderDTO.builder()
                .id(bidder.getId())
                .name(bidder.getName())
                .money(bidder.getMoney())
                .reservedMoney(bidder.getReservedMoney())
                .password(bidder.getPassword())
                .build();
    }
}
