package com.example.auctionback.services;

import com.example.auctionback.controllers.models.LotDTO;
import com.example.auctionback.controllers.models.OrderDTO;
import com.example.auctionback.database.entities.Lot;
import com.example.auctionback.database.entities.Item;
import com.example.auctionback.database.entities.Bidder;
import com.example.auctionback.database.entities.Order;
import com.example.auctionback.database.repository.OrderRepository;
import com.example.auctionback.exceptions.*;
import com.example.auctionback.database.repository.LotRepository;
import com.example.auctionback.database.repository.ItemRepository;
import com.example.auctionback.database.repository.BidderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LotService {

    private final LotRepository lotRepository;
    private final ItemRepository itemRepository;
    private final BidderRepository bidderRepository;
    private final OrderRepository orderRepository;


    public LotDTO createNewLot(LotDTO lotDTO)
            throws LotAlreadyExistException, ItemNotFoundException {

        if (!itemRepository.existsById(lotDTO.getItemId()))
            throw new ItemNotFoundException();

        if (lotRepository.existsByItemId(lotDTO.getItemId()))
            throw new LotAlreadyExistException();

        Lot lot = Lot.builder()
                .id(lotDTO.getId())
                .title(lotDTO.getTitle())
                .description(lotDTO.getDescription())
                .itemId(lotDTO.getItemId())
                .minBidIncrease(lotDTO.getMinBidIncrease())
                .ownerId(lotDTO.getOwnerId())
                .build();

        lotRepository.save(lot);
        // todo: mapper https://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application
        return LotDTO.builder()
                .id(lotDTO.getId())
                .title(lotDTO.getTitle())
                .description(lotDTO.getDescription())
                .itemId(lotDTO.getItemId())
                .minBidIncrease(lotDTO.getMinBidIncrease())
                .ownerId(lotDTO.getOwnerId())
                .build();

    }

    public LotDTO getLot(Long lotId) throws LotNotFoundException {
        Lot lot = lotRepository.findById(lotId).orElseThrow(LotNotFoundException::new);

        return LotDTO.builder()
                .id(lot.getId())
                .title(lot.getTitle())
                .description(lot.getDescription())
                .itemId(lot.getItemId())
                .minBidIncrease(lot.getMinBidIncrease())
                .build();
    }

    public List<LotDTO> getAllLots() {
        List<Lot> allLots = (List<Lot>) lotRepository.findAll();

        return allLots.stream()
                .map(lot -> LotDTO.builder()
                        .id(lot.getId())
                        .title(lot.getTitle())
                        .description(lot.getDescription())
                        .itemId(lot.getItemId())
                        .minBidIncrease(lot.getMinBidIncrease())
                        .ownerId(lot.getOwnerId())
                        .build())
                .collect(Collectors.toList());
    }

    public OrderDTO updateAuction(OrderDTO bidRequest)
            throws LotNotFoundException, BidderNotFoundException, NotEnoughtMoneyException {

        List<Order> allOrders = orderRepository.findByAuctionId(bidRequest.getAuctionId());
        Order currentOrder = null;
        for (var o : allOrders)
            if (o.isOrderStatus())
            {
                currentOrder = o;
                break;
            }

        Order newOrder = new Order(
                bidRequest.getOrderId(),
                bidRequest.getOrderOwnerId(),
                bidRequest.getOrderPrice(),
                bidRequest.getItemId(),
                bidRequest.getAuctionId(),
                new Date(),
                false
                );

        if (newOrder.getPrice() <= currentOrder.getPrice())
            throw NotEnoughtMoneyException;
        else
            newOrder.setOrderStatus(true);


        if (currentOrder != null) {
            currentOrder.setOrderStatus(false);
            this.unlockMoney(currentOrder.getOwnerId(), currentOrder.getPrice());
        }

        this.lockMoney(newOrder.getOwnerId(), newOrder.getPrice())
        orderRepository.save(currentOrder);
        orderRepository.save(newOrder);
        return newOrder;
    /*
        //todo: проверить на то что ставка проходит минимальный increase
        Lot lot = lotRepository.findById(bidRequest.getLotId()).
                orElseThrow(LotNotFoundException::new);



        this.lockMoney(bidRequest);
        if (lot.getBidOwnerId() != 0)
            this.unlockMoney(bidRequest);

        lot.setBidCost(bidRequest.getNextBid());
        lot.setBidOwnerId(bidRequest.getNewBidderId());
        lotRepository.save(lot);
        return null;*/

    }

/*    public String deleteAuction(Long auctionId)
            throws LotNotFoundException {

        Lot lot = lotRepository.findById(auctionId).orElseThrow(LotNotFoundException::new);
        if (lot.getBidOwnerId() != 0){
            Bidder bidder = bidderRepository.findById(lot.getBidOwnerId()).orElseThrow();
            bidder.setReservedMoney(bidder.getReservedMoney() - lot.getBidCost());
        }

        lotRepository.deleteById(auctionId);
        return null;
    }
*/
    public String finishAuction(Long auctionId)
            throws LotNotFoundException {

        Lot lot = lotRepository.findById(auctionId).orElseThrow(LotNotFoundException::new);

        List<Order> allOrders = orderRepository.findByAuctionId(auctionId);
        Order currentOrder = null;
        for (var o : allOrders)
            if (o.isOrderStatus())
            {
                currentOrder = o;
                break;
            }


        lot.setFinishAt(new Date());
        lot.setLotStatus(true);


        if (currentOrder != null){
            Long userId = itemRepository.findById(lot.getItemId()).orElseThrow().getOwnerId(); //get owner id item
            this.transferMoney(currentOrder.getOwnerId(), userId, currentOrder.getPrice());
            this.transferItem(currentOrder.getOwnerId(), lot.getItemId());
        }






        Lot lot = lotRepository.findById(auctionId).orElseThrow(LotNotFoundException::new);

        if (lot.getBidOwnerId() != 0) {
            Long id = itemRepository.findById(lot.getItemId()).orElseThrow().getOwnerId(); //get owner id item
            this.transferMoney(lot.getBidOwnerId(), id, lot.getBidCost());
            this.transferItem(lot.getBidOwnerId(), lot.getItemId());
        }

        lotRepository.deleteById(auctionId);
        return null;
    }

    private void lockMoney(Long userId, Long money) throws BidderNotFoundException, NotEnoughtMoneyException {
/*
        Bidder bidder = bidderRepository.findById(bidRequest.getNewBidderId()).
                orElseThrow(BidderNotFoundException::new);

        if (bidder.getMoney() < bidRequest.getNextBid())
            throw new NotEnoughtMoneyException();

        bidder.setReservedMoney(bidder.getReservedMoney() + bidRequest.getNextBid());
        bidder.setMoney(bidder.getMoney() - bidder.getReservedMoney());
        bidderRepository.save(bidder);*/
    }

    private void unlockMoney(Long userId, Long money) throws BidderNotFoundException {

        /*Lot lot = lotRepository.findById(bidRequest.getLotId()).orElseThrow();
        Bidder bidder = bidderRepository.findById(lot.getBidOwnerId()).
                orElseThrow(BidderNotFoundException::new);

        bidder.setReservedMoney(bidder.getReservedMoney() - bidRequest.getNextBid());
        bidder.setMoney(bidder.getMoney() + bidder.getReservedMoney());
        bidderRepository.save(bidder);*/
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