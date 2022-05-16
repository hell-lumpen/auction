package com.example.auctionback.controllers;

import com.example.auctionback.controllers.models.OrderDTO;
import com.example.auctionback.controllers.models.LotDTO;
import com.example.auctionback.exceptions.*;
import com.example.auctionback.security.models.OurAuthToken;
import com.example.auctionback.services.LotService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@AllArgsConstructor
public class LotController {
    private final LotService lotService;

    @PostMapping("/private/auction")
    public LotDTO createAuction(@RequestBody LotDTO auctionRequest, OurAuthToken token)
            throws LotAlreadyExistException, ItemNotFoundException,
            DataNotCorrectException{
        return lotService.createNewLot(auctionRequest, token);
    }

    @GetMapping("/private/auction/{id}")
    public LotDTO getAuction(@PathVariable("id") Long auctionId, OurAuthToken token) throws LotNotFoundException {
        return lotService.getLot(auctionId, token);
    }

    @GetMapping("/private/auction/{id}/orders")
    public List<OrderDTO> getAllOrder(@PathVariable("id") Long auctionId) throws LotNotFoundException {
        return lotService.getAllOrders(auctionId);
    }

    @GetMapping("/public/auction/all")
    public List<LotDTO> getAllAuctions() {
        return lotService.getAllLots();
    }

    @PutMapping("/private/auction/{id}/order")
    public OrderDTO createNewBid(@PathVariable("id") Long auctionId, @RequestBody OrderDTO bidRequest, OurAuthToken token)
            throws NotEnoughMoneyException, BidderNotFoundException, LotNotFoundException, DataNotCorrectException, InvalidOrderPriceException, ParticipateInYourAuctionException {
        return lotService.updateAuction(auctionId, bidRequest, token);
    }
}
