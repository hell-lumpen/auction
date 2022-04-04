package com.example.auctionback.services;

import com.example.auctionback.controllers.models.LotDTO;
import com.example.auctionback.controllers.models.BidDTO;
import com.example.auctionback.database.entities.Lot;
import com.example.auctionback.database.entities.Item;
import com.example.auctionback.database.entities.Bidder;
import com.example.auctionback.exceptions.*;
import com.example.auctionback.database.repository.LotRepository;
import com.example.auctionback.database.repository.ItemRepository;
import com.example.auctionback.database.repository.BidderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LotService {

    private final LotRepository lotRepository;
    private final ItemRepository itemRepository;
    private final BidderRepository bidderRepository;

    public LotDTO createNewAuction(LotDTO lotDTO)
            throws LotAlreadyExistException, ItemNotFoundException {

        if (!itemRepository.existsById(lotDTO.getItemId()))
            throw new ItemNotFoundException();

        if (lotRepository.existsByItemId(lotDTO.getItemId()))
            throw new LotAlreadyExistException();

        Lot lot = Lot.builder().title(lotDTO.getTitle()).itemId(lotDTO.getItemId()).bidCost(lotDTO.getBidCost())
                .minBidIncrease(lotDTO.getMinBidIncrease()).bidOwnerId(lotDTO.getBidOwnerId()).build();

        lotRepository.save(lot);
        return LotDTO.builder()
                .id(lot.getId())
                .title(lot.getTitle())
                .itemId(lot.getItemId())
                .bidCost(lot.getBidCost())
                .minBidIncrease(lot.getMinBidIncrease())
                .bidOwnerId(lot.getBidOwnerId())
                .build();

    }

    public LotDTO getAuction(Long auctionId) throws LotNotFoundException {
        Lot lot = lotRepository.findById(auctionId).orElseThrow(LotNotFoundException::new);

        return LotDTO.builder().id(lot.getId()).
                title(lot.getTitle()).itemId(lot.getItemId()).
                bidCost(lot.getBidCost()).minBidIncrease(lot.getMinBidIncrease())
                .bidOwnerId(lot.getBidOwnerId()).build();
    }

    public List<LotDTO> getAllAuctions() {
        List<Lot> allLots = (List<Lot>) lotRepository.findAll();

        return allLots.stream()
                .map(lot -> LotDTO.builder().id(lot.getId()).
                        title(lot.getTitle()).itemId(lot.getItemId()).
                        bidCost(lot.getBidCost()).minBidIncrease(lot.getMinBidIncrease())
                        .bidOwnerId(lot.getBidOwnerId()).build())
                .collect(Collectors.toList());
    }

    public LotDTO updateAuction(BidDTO bidRequest)
            throws LotNotFoundException, BidderNotFoundException, NotEnoughtMoneyException {

        //todo: проверить на то что ставка проходит минимальный increase
        Lot lot = lotRepository.findById(bidRequest.getLotId()).
                orElseThrow(LotNotFoundException::new);

        this.lockMoney(bidRequest);
        if (lot.getBidOwnerId() != 0)
            this.unlockMoney(bidRequest);

        lot.setBidCost(bidRequest.getNextBid());
        lot.setBidOwnerId(bidRequest.getNewBidderId());
        lotRepository.save(lot);
        return null;
    }

    public String deleteAuction(Long auctionId)
            throws LotNotFoundException {

        Lot lot = lotRepository.findById(auctionId).orElseThrow(LotNotFoundException::new);
        if (lot.getBidOwnerId() != 0){
            Bidder bidder = bidderRepository.findById(lot.getBidOwnerId()).orElseThrow();
            bidder.setReservedMoney(bidder.getReservedMoney() - lot.getBidCost());
        }

        lotRepository.deleteById(auctionId);
        return null;
    }

    public String finishAuction(Long auctionId)
            throws LotNotFoundException {

        Lot lot = lotRepository.findById(auctionId).orElseThrow(LotNotFoundException::new);

        if (lot.getBidOwnerId() != 0) {
            Long id = itemRepository.findById(lot.getItemId()).orElseThrow().getOwnerId(); //get owner id item
            this.transferMoney(lot.getBidOwnerId(), id, lot.getBidCost());
            this.transferItem(lot.getBidOwnerId(), lot.getItemId());
        }

        lotRepository.deleteById(auctionId);
        return null;
    }

    private void lockMoney(BidDTO bidRequest) throws BidderNotFoundException, NotEnoughtMoneyException {

        Bidder bidder = bidderRepository.findById(bidRequest.getNewBidderId()).
                orElseThrow(BidderNotFoundException::new);

        if (bidder.getMoney() < bidRequest.getNextBid())
            throw new NotEnoughtMoneyException();

        bidder.setReservedMoney(bidder.getReservedMoney() + bidRequest.getNextBid());
        bidder.setMoney(bidder.getMoney() - bidder.getReservedMoney());
        bidderRepository.save(bidder);
    }

    private void unlockMoney(BidDTO bidRequest) throws BidderNotFoundException {

        Lot lot = lotRepository.findById(bidRequest.getLotId()).orElseThrow();
        Bidder bidder = bidderRepository.findById(lot.getBidOwnerId()).
                orElseThrow(BidderNotFoundException::new);

        bidder.setReservedMoney(bidder.getReservedMoney() - bidRequest.getNextBid());
        bidder.setMoney(bidder.getMoney() + bidder.getReservedMoney());
        bidderRepository.save(bidder);
    }

    private void transferMoney(Long sourceId, Long destinationId, float cost) {

        Bidder bidder1 = bidderRepository.findById(sourceId).orElseThrow();
        Bidder bidder2 = bidderRepository.findById(destinationId).orElseThrow();

        bidder1.setReservedMoney(bidder1.getReservedMoney() - cost);
        bidder2.setMoney(bidder2.getMoney() + cost);
        bidderRepository.save(bidder1);
        bidderRepository.save(bidder2);

    }

    private void transferItem(Long destinationId, Long itemId){
        Item item = itemRepository.findById(itemId).orElseThrow();
        item.setOwnerId(destinationId);
        itemRepository.save(item);
    }
}