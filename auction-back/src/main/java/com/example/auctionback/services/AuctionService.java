package com.example.auctionback.services;

import com.example.auctionback.controllers.models.AuctionRequest;
import com.example.auctionback.controllers.models.AuctionResponse;
import com.example.auctionback.controllers.models.BidRequest;
import com.example.auctionback.entities.Auction;
import com.example.auctionback.entities.Item;
import com.example.auctionback.entities.Slave;
import com.example.auctionback.exceptions.*;
import com.example.auctionback.repository.AuctionRepository;
import com.example.auctionback.repository.ItemRepository;
import com.example.auctionback.repository.SlaveRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final ItemRepository itemRepository;
    private final SlaveRepository slaveRepository;


    public AuctionResponse createNewAuction(AuctionRequest auctionRequest)
            throws AuctionAlreadyExistException, ItemNotFoundException {

        if (!itemRepository.existsById(auctionRequest.getItemId()))
            throw new ItemNotFoundException();

        if (auctionRepository.existsByItemId(auctionRequest.getItemId()))
            throw new AuctionAlreadyExistException();

        Auction auction = new Auction(
                auctionRequest.getName(),
                auctionRequest.getItemId(),
                auctionRequest.getBidCost(),
                auctionRequest.getMinBidIncrease(),
                auctionRequest.getBidOwnerId()
        );

        auctionRepository.save(auction);

        return new AuctionResponse(
                auction.getId(),
                auction.getName(),
                auction.getItemId(),
                auction.getBidCost(),
                auction.getMinBidIncrease(),
                auction.getBidOwnerId()
        );
    }

    public AuctionResponse getAuction(Long auctionId) throws AuctionNotFoundException {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(AuctionNotFoundException::new);

        return new AuctionResponse(
                auction.getId(),
                auction.getName(),
                auction.getItemId(),
                auction.getBidCost(),
                auction.getMinBidIncrease(),
                auction.getBidOwnerId());
    }

    public List<AuctionResponse> getAllAuctionsFromDB() {
        List<Auction> allAuctions = (List<Auction>) auctionRepository.findAll();

        return allAuctions.stream()
                .map(auction -> new AuctionResponse(
                        auction.getId(),
                        auction.getName(),
                        auction.getItemId(),
                        auction.getBidCost(),
                        auction.getMinBidIncrease(),
                        auction.getBidOwnerId()))
                .collect(Collectors.toList());
    }

    public AuctionResponse updateAuction(BidRequest bidRequest)
            throws AuctionNotFoundException, SlaveNotExistException, NotEnoughtMoneyException {

        //todo: проверить на то что ставка проходит минимальный increase
        Auction auction = auctionRepository.findById(bidRequest.getAuctionId()).
                orElseThrow(AuctionNotFoundException::new);

        this.lockMoney(bidRequest);
        if (auction.getBidOwnerId() != 0)
            this.unlockMoney(bidRequest);

        auction.setBidCost(bidRequest.getCurrentBid());
        auction.setBidOwnerId(bidRequest.getOwnerId());
        return null;
    }

    public String deleteAuction(Long auctionId)
            throws AuctionNotFoundException {

        Auction auction = auctionRepository.findById(auctionId).orElseThrow(AuctionNotFoundException::new);
        if (auction.getBidOwnerId() != 0){
            Slave slave = slaveRepository.findById(auction.getBidOwnerId()).orElseThrow();
            slave.setReservedMoney(slave.getReservedMoney() - auction.getBidCost());
        }

        auctionRepository.deleteById(auctionId);
        return null;
    }

    public void finishAuction(Long auctionId)
            throws AuctionNotFoundException {

        Auction auction = auctionRepository.findById(auctionId).orElseThrow(AuctionNotFoundException::new);

        if (auction.getBidOwnerId() != 0) {
            Long id = itemRepository.findById(auction.getItemId()).orElseThrow().getOwnerId(); //get owner id item
            this.transferMoney(auction.getBidOwnerId(), id, auction.getBidCost());
            this.transferItem(auction.getBidOwnerId(), auction.getItemId());
        }

        auctionRepository.deleteById(auctionId);
    }

    private void lockMoney(BidRequest bidRequest) throws SlaveNotExistException, NotEnoughtMoneyException {

        Slave slave = slaveRepository.findById(bidRequest.getOwnerId()).
                orElseThrow(SlaveNotExistException::new);

        if (slave.getMoney() < bidRequest.getCurrentBid())
            throw new NotEnoughtMoneyException();

        slave.setReservedMoney(slave.getReservedMoney() + bidRequest.getCurrentBid());
        slave.setMoney(slave.getMoney() - slave.getReservedMoney());
    }

    private void unlockMoney(BidRequest bidRequest) throws SlaveNotExistException {

        Auction auction = auctionRepository.findById(bidRequest.getAuctionId()).orElseThrow();
        Slave slave = slaveRepository.findById(auction.getBidOwnerId()).
                orElseThrow(SlaveNotExistException::new);

        slave.setReservedMoney(slave.getReservedMoney() - bidRequest.getCurrentBid());
        slave.setMoney(slave.getMoney() + slave.getReservedMoney());
    }

    private void transferMoney(Long sourceId, Long destinationId, float cost) {

        Slave slave1 = slaveRepository.findById(sourceId).orElseThrow();
        Slave slave2 = slaveRepository.findById(destinationId).orElseThrow();

        slave1.setReservedMoney(slave1.getReservedMoney() - cost);
        slave2.setMoney(slave2.getMoney() + cost);
    }

    private void transferItem(Long destinationId, Long itemId){
        Item item = itemRepository.findById(itemId).orElseThrow();
        item.setOwnerId(destinationId);
    }
}