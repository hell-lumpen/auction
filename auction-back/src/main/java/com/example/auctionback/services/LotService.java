package com.example.auctionback.services;

import com.example.auctionback.controllers.models.LotDTO;
import com.example.auctionback.controllers.models.OrderDTO;
import com.example.auctionback.database.entities.*;
import com.example.auctionback.database.repository.OrderRepository;
import com.example.auctionback.exceptions.*;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper mapper;

    public LotDTO createNewLot(LotDTO lotDTO)
            throws LotAlreadyExistException, ItemNotFoundException {

        if (!itemRepository.existsById(lotDTO.getItemId()))
            throw new ItemNotFoundException();

        if (lotRepository.existsByItemId(lotDTO.getItemId()))
            throw new LotAlreadyExistException();

        Lot lot = mapper.map(lotDTO, Lot.class);

        lotRepository.save(lot);
        // todo: mapper https://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application
        return lotDTO;

    }

    public LotDTO getLot(Long lotId) throws LotNotFoundException {
        Lot lot = lotRepository.findById(lotId).orElseThrow(LotNotFoundException::new);
        return mapper.map(lot, LotDTO.class);
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
            if (o.getOrderStatus())
            {
                currentOrder = o;
                break;
            }

        Order newOrder = mapper.map(bidRequest, Order.class);
        newOrder.setCreatedAt(new Date());
        newOrder.setOrderStatus(false);

         if (currentOrder == null) {
            MoneyValue newMoney = new MoneyValue(newOrder.getOrderPrice());
            this.lockMoney(newOrder.getOrderOwnerNickname(), newMoney);
            orderRepository.save(newOrder);
            return mapper.map(newOrder, OrderDTO.class);
        }

        MoneyValue newMoney = new MoneyValue(newOrder.getOrderPrice());
        MoneyValue currentMoney = new MoneyValue(currentOrder.getOrderPrice());

        if (newMoney.lessThen(currentMoney))
            throw new NotEnoughtMoneyException();


        newOrder.setOrderStatus(true);

        currentOrder.setOrderStatus(false);
        this.unlockMoney(currentOrder.getOrderOwnerNickname(), currentMoney);
        this.lockMoney(newOrder.getOrderOwnerNickname(), newMoney);
        orderRepository.save(currentOrder);
        orderRepository.save(newOrder);


        return mapper.map(newOrder, OrderDTO.class);
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


    public String finishAuction(Long auctionId)
            throws LotNotFoundException {

        Lot lot = lotRepository.findById(auctionId).orElseThrow(LotNotFoundException::new);

        List<Order> allOrders = orderRepository.findByAuctionId(auctionId);
        Order currentOrder = null;
        for (var order : allOrders) {
            if (order.getOrderStatus()) {
                currentOrder = order;
                break;
            }
        }

        lot.setFinishAt(new Date());
        lot.setLotStatus(true); // its can sell

        if (currentOrder != null){
            String userId = itemRepository.findById(lot.getItemId()).orElseThrow().getOwnerNickname(); // get owner id item
            this.transferMoney(currentOrder.getOrderOwnerNickname(), userId,
                    new MoneyValue(currentOrder.getOrderPrice()));
            this.transferItem(currentOrder.getOrderOwnerNickname(), lot.getItemId());
        }

        lotRepository.save(lot);
        return null;
    }

    private void lockMoney(String bidderNickname, MoneyValue money) throws BidderNotFoundException, NotEnoughtMoneyException {
        Bidder bidder = bidderRepository.findOptionalByNickname(bidderNickname).orElseThrow();//можно прокинуть ошибку
        MoneyValue currentMoney = new MoneyValue(bidder.getMoney());
        MoneyValue reversedMoney = new MoneyValue(bidder.getReservedMoney());
        if (currentMoney.lessThen(money))
            throw new NotEnoughtMoneyException();

        reversedMoney.Add(money);
        currentMoney.Diff(money);
        bidder.setMoney(currentMoney.toString());
        bidder.setReservedMoney(reversedMoney.toString());
        bidderRepository.save(bidder);
    }

    private void unlockMoney(String bidderNickname, MoneyValue money) throws BidderNotFoundException {
        Bidder bidder = bidderRepository.findOptionalByNickname(bidderNickname).orElseThrow();//можно прокинуть ошибку
        MoneyValue currentMoney = new MoneyValue(bidder.getMoney());
        MoneyValue reversedMoney = new MoneyValue(bidder.getReservedMoney());
        reversedMoney.Diff(money);
        currentMoney.Add(money);
        bidder.setMoney(currentMoney.toString());
        bidder.setReservedMoney(reversedMoney.toString());
        bidderRepository.save(bidder);
    }

    private void transferMoney(String sourceNickname, String destinationNickname, MoneyValue cost) {
        Bidder bidder1 = bidderRepository.findOptionalByNickname(sourceNickname).orElseThrow();
        Bidder bidder2 = bidderRepository.findOptionalByNickname(destinationNickname).orElseThrow();

        bidder1.setReservedMoney(new MoneyValue(bidder1.getReservedMoney()).Diff(cost).toString());
        bidder2.setMoney(bidder2.getMoney() + cost);
        bidderRepository.save(bidder1);
        bidderRepository.save(bidder2);

    }

    private void transferItem(String destinationNickname, Long itemId){
        Item item = itemRepository.findById(itemId).orElseThrow();
        item.setOwnerNickname(destinationNickname);
        itemRepository.save(item);
    }

}