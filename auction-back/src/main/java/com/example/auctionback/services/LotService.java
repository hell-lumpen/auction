package com.example.auctionback.services;

import com.example.auctionback.controllers.models.LotDTO;
import com.example.auctionback.controllers.models.OrderDTO;
import com.example.auctionback.database.entities.*;
import com.example.auctionback.database.repository.OrderRepository;
import com.example.auctionback.exceptions.*;
import com.example.auctionback.security.models.OurAuthToken;
import org.modelmapper.ModelMapper;
import com.example.auctionback.database.repository.LotRepository;
import com.example.auctionback.database.repository.ItemRepository;
import com.example.auctionback.database.repository.BidderRepository;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public LotDTO createNewLot(LotDTO lotDTO, OurAuthToken token)
            throws LotAlreadyExistException, ItemNotFoundException,
            DataNotCorrectException{

        if (!itemRepository.existsById(lotDTO.getItemId()))
            throw new ItemNotFoundException();

        if (lotRepository.existsByItemId(lotDTO.getItemId()))
            throw new LotAlreadyExistException();

        if (lotDTO.getTitle() == null)
            throw new DataNotCorrectException();

        if (lotDTO.getFinishAt() == null)
            throw new DataNotCorrectException();

        if (lotDTO.getMinBidIncrease() == null) {
            lotDTO.setMinBidIncrease("50");
        }

        lotDTO.setOwnerNickname(token.getPrincipal().getNickname());
        lotDTO.setCurrentCost(lotDTO.getStartCost());
        Lot lot = mapper.map(lotDTO, Lot.class);
        lot.setCreateAt(LocalDateTime.now());
        LocalDateTime localDateTime = LocalDateTime.parse(lotDTO.getFinishAt(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        lot.setFinishAt(localDateTime);
        lotRepository.save(lot);
        lotDTO.setId(lot.getId());

        // todo: mapper https://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application
        return lotDTO;

    }

    public LotDTO getLot(Long lotId, OurAuthToken token) throws LotNotFoundException {
        Lot lot = lotRepository.findById(lotId).orElseThrow(LotNotFoundException::new);
        String currentCost;
        if (lot.getLastOrderId() != null) {
            var lastOrder = orderRepository.findByOrderId(lot.getLastOrderId());
            currentCost = lastOrder.getOrderPrice();
        }
        else {
            currentCost = lot.getStartCost();
        }

        return LotDTO.builder()
                .id(lot.getItemId())
                .title(lot.getTitle())
                .description(lot.getDescription())
                .itemId(lot.getItemId())
                .startCost(lot.getStartCost())
                .currentCost(currentCost)
                .finishAt(lot.getFinishAt().toString())
                .minBidIncrease(lot.getMinBidIncrease())
                .ownerNickname(lot.getOwnerNickname())
                .build();
    }

    private String getCurrentCost(Lot lot){
        if (lot.getLastOrderId()!= null){
            return orderRepository.findByOrderId(lot.getLastOrderId()).getOrderPrice();
        }
        else{
            return lot.getStartCost();
        }
    }

    public List<LotDTO> getAllLots() {
        List<Lot> allLots = (List<Lot>) lotRepository.findAll();


        return allLots.stream()
                .map(lot -> {
                    return LotDTO.builder()
                            .id(lot.getId())
                            .title(lot.getTitle())
                            .description(lot.getDescription())
                            .itemId(lot.getItemId())
                            .finishAt(lot.getFinishAt().toString())
                            .startCost(lot.getStartCost())
                            .currentCost(getCurrentCost(lot))
                            .minBidIncrease(lot.getMinBidIncrease())
                            .ownerNickname(lot.getOwnerNickname())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public OrderDTO updateAuction(Long auctionId, OrderDTO bidRequest, OurAuthToken token)
            throws LotNotFoundException, BidderNotFoundException, NotEnoughMoneyException, DataNotCorrectException, InvalidOrderPriceException, ParticipateInYourAuctionException {

        List<Order> allOrders = orderRepository.findByAuctionId(auctionId);

        bidRequest.setAuctionId(auctionId);
        bidRequest.setOrderOwnerNickname(token.getPrincipal().getNickname());
        bidRequest.setCreatedAt(LocalDateTime.now());
        var lot = lotRepository.findById(auctionId).orElseThrow(LotNotFoundException::new);
        if (lot.isLotStatus())
            throw new DataNotCorrectException();//todo:поменять на ошибку о том, что аукцион закончился

        if (lot.getOwnerNickname().equals(token.getPrincipal().getNickname())) {
            throw new ParticipateInYourAuctionException();
        }

        bidRequest.setItemId(lot.getItemId());

        MoneyValue bidRequestMoney = new MoneyValue(bidRequest.getOrderPrice());
        MoneyValue minBidIncreaseMoney = new MoneyValue(lot.getMinBidIncrease());

        if (lot.getLastOrderId() != null) {
            Order nowOrderLot = orderRepository.findById(lot.getLastOrderId()).orElseThrow();
            minBidIncreaseMoney = minBidIncreaseMoney.Add(new MoneyValue(nowOrderLot.getOrderPrice()));
        }else
            minBidIncreaseMoney = minBidIncreaseMoney.Add(new MoneyValue(lot.getStartCost()));


        if (bidRequestMoney.lessThen(minBidIncreaseMoney)) {
            throw new InvalidOrderPriceException();
        }

        Order newOrder = mapper.map(bidRequest, Order.class);
        MoneyValue newMoney = new MoneyValue(newOrder.getOrderPrice());

        if (lot.getLastOrderId() != null) {
            Order currentOrder = orderRepository.findByOrderId(lot.getLastOrderId());
            MoneyValue currentMoney = new MoneyValue(currentOrder.getOrderPrice());
            if (newMoney.lessThen(currentMoney)) {
                throw new NotEnoughMoneyException();
            }

            currentOrder.setOrderStatus(false);
            this.unlockMoney(currentOrder.getOrderOwnerNickname(), currentMoney);
            orderRepository.save(currentOrder);
        }




        newOrder.setOrderStatus(true);
        this.lockMoney(newOrder.getOrderOwnerNickname(), newMoney);
        orderRepository.save(newOrder);
        lot.setLastOrderId(newOrder.getOrderId());
        lotRepository.save(lot);

        return OrderDTO.builder()
                .orderId(newOrder.getOrderId())
                .orderOwnerNickname(newOrder.getOrderOwnerNickname())
                .orderPrice(newOrder.getOrderPrice())
                .itemId(newOrder.getItemId())
                .auctionId(newOrder.getAuctionId())
                .createdAt(newOrder.getCreatedAt())
                .orderStatus(newOrder.getOrderStatus())
                .build();


        /*Order currentOrder = null;
        for (var o : allOrders) {
            if (o.getOrderStatus()) {
                currentOrder = o;
                break;
            }
        }

        Order newOrder = mapper.map(bidRequest, Order.class);
        newOrder.setCreatedAt(new Date());
        newOrder.setOrderStatus(false);
        System.out.println(newOrder.getOrderId());
        if (currentOrder == null) {
            MoneyValue newMoney = new MoneyValue(newOrder.getOrderPrice());
            this.lockMoney(newOrder.getOrderOwnerNickname(), newMoney);
            orderRepository.save(newOrder);
            lot.setLastOrderId(newOrder.getOrderId());
            lotRepository.save(lot);
            return OrderDTO.builder()
                    .orderId(newOrder.getOrderId())
                    .orderOwnerNickname(newOrder.getOrderOwnerNickname())
                    .orderPrice(newOrder.getOrderPrice())
                    .itemId(newOrder.getItemId())
                    .auctionId(newOrder.getAuctionId())
                    .createdAt(newOrder.getCreatedAt())
                    .orderStatus(newOrder.getOrderStatus())
                    .build();
        }

        MoneyValue newMoney = new MoneyValue(newOrder.getOrderPrice());
        MoneyValue currentMoney = new MoneyValue(currentOrder.getOrderPrice());

        if (newMoney.lessThen(currentMoney))
            throw new NotEnoughMoneyException();


        newOrder.setOrderStatus(true);

        currentOrder.setOrderStatus(false);
        this.unlockMoney(currentOrder.getOrderOwnerNickname(), currentMoney);
        this.lockMoney(newOrder.getOrderOwnerNickname(), newMoney);
        orderRepository.save(currentOrder);
        orderRepository.save(newOrder);
        lot.setLastOrderId(newOrder.getOrderId());
        lotRepository.save(lot);
        return OrderDTO.builder()
                .orderId(newOrder.getOrderId())
                .orderOwnerNickname(newOrder.getOrderOwnerNickname())
                .orderPrice(newOrder.getOrderPrice())
                .itemId(newOrder.getItemId())
                .auctionId(newOrder.getAuctionId())
                .createdAt(newOrder.getCreatedAt())
                .orderStatus(newOrder.getOrderStatus())
                .build();*/


    }


    @Scheduled(cron = "0 * * ? * *")
    private void checkTimeForFinishAuction()
            throws LotNotFoundException{
        System.out.println("hui");
        var lots = lotRepository.findAllByLotStatus(false);
        for (var lot : lots) {
            if (!lot.getFinishAt().isAfter(LocalDateTime.now())){
                finishAuction(lot.getId());
            }
        }
    }




    public String finishAuction(Long auctionId)
            throws LotNotFoundException {

        Lot lot = lotRepository.findById(auctionId).orElseThrow(LotNotFoundException::new);

//        List<Order> allOrders = orderRepository.findByAuctionId(auctionId);
//        Order currentOrder = null;
//        for (var order : allOrders) {
//            if (order.getOrderStatus()) {
//                currentOrder = order;
//                break;
//            }
//        }

//        lot.setFinishAt(new Date());
        lot.setLotStatus(true); // its can sell

        if (lot.getLastOrderId() != null){
            Order currentOrder = orderRepository.findByOrderId(lot.getLastOrderId());
            String userId = itemRepository.findById(lot.getItemId()).orElseThrow().getOwner().getNickname(); // get owner id item
            this.transferMoney(currentOrder.getOrderOwnerNickname(), userId,
                    new MoneyValue(currentOrder.getOrderPrice()));
            this.transferItem(currentOrder.getOrderOwnerNickname(), lot.getItemId());
        }

        lotRepository.save(lot);
        return null;
    }

    private void lockMoney(String bidderNickname, MoneyValue money) throws BidderNotFoundException, NotEnoughMoneyException {
        Bidder bidder = bidderRepository.findOptionalByNickname(bidderNickname).orElseThrow();//можно прокинуть ошибку
        MoneyValue currentMoney = new MoneyValue(bidder.getMoney());
        MoneyValue reversedMoney = new MoneyValue(bidder.getReservedMoney());
        if (currentMoney.lessThen(money))
            throw new NotEnoughMoneyException();

        reversedMoney = reversedMoney.Add(money);
        currentMoney = currentMoney.Diff(money);
        bidder.setMoney(currentMoney.toString());
        bidder.setReservedMoney(reversedMoney.toString());
        bidderRepository.save(bidder);
    }

    private void unlockMoney(String bidderNickname, MoneyValue money) throws BidderNotFoundException {
        Bidder bidder = bidderRepository.findOptionalByNickname(bidderNickname).orElseThrow();//можно прокинуть ошибку
        MoneyValue currentMoney = new MoneyValue(bidder.getMoney());
        MoneyValue reversedMoney = new MoneyValue(bidder.getReservedMoney());
        reversedMoney = reversedMoney.Diff(money);
        currentMoney = currentMoney.Add(money);
        bidder.setMoney(currentMoney.toString());
        bidder.setReservedMoney(reversedMoney.toString());
        bidderRepository.save(bidder);
    }

    private void transferMoney(String sourceNickname, String destinationNickname, MoneyValue cost) {
        Bidder bidder1 = bidderRepository.findOptionalByNickname(sourceNickname).orElseThrow();
        Bidder bidder2 = bidderRepository.findOptionalByNickname(destinationNickname).orElseThrow();

        bidder1.setReservedMoney(new MoneyValue(bidder1.getReservedMoney()).Diff(cost).toString());
        MoneyValue bid2 = new MoneyValue(bidder2.getMoney());
        bid2 = bid2.Add(cost);
        bidder2.setMoney(bid2.toString());
        bidderRepository.save(bidder1);
        bidderRepository.save(bidder2);

    }

    private void transferItem(String destinationNickname, Long itemId){
        Item item = itemRepository.findById(itemId).orElseThrow();
        item.setOwner(bidderRepository.findOptionalByNickname(destinationNickname).orElseThrow());
        itemRepository.save(item);
    }

    public List<OrderDTO> getAllOrders(Long auctionId) throws LotNotFoundException {
        lotRepository.findById(auctionId).orElseThrow(LotNotFoundException::new);
        var orders =  orderRepository.findByAuctionId(auctionId);

        return orders.stream()
                .map(order -> OrderDTO.builder()
                        .orderId(order.getOrderId())
                        .orderOwnerNickname(order.getOrderOwnerNickname())
                        .orderPrice(order.getOrderPrice())
                        .itemId(order.getItemId())
                        .auctionId(order.getAuctionId())
                        .createdAt(order.getCreatedAt())
                        .orderStatus(order.getOrderStatus())
                        .build())
                .collect(Collectors.toList());
    }
}