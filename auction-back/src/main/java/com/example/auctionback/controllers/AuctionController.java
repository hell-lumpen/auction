package com.example.auctionback.controllers;

import com.example.auctionback.controllers.models.AuctionRequest;
import com.example.auctionback.controllers.models.AuctionResponse;
import com.example.auctionback.controllers.models.BidRequest;
import com.example.auctionback.exceptions.*;
import com.example.auctionback.services.AuctionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auction")
@AllArgsConstructor
public class AuctionController {
    private final AuctionService auctionService;

    @PostMapping("/new_auction")
    public AuctionResponse createAuction(@RequestBody AuctionRequest auctionRequest)
            throws AuctionAlreadyExistException, ItemNotFoundException {
        return auctionService.createNewAuction(auctionRequest);
    }

    @GetMapping("/{id}")
    public AuctionResponse getAuction(@PathVariable("id") Long auctionId) throws AuctionNotFoundException {
        return auctionService.getAuction(auctionId);
    }

    @GetMapping("/get_all_auctions")
    public List<AuctionResponse> getAllAuctions() {
        return auctionService.getAllAuctionsFromDB();
    }

    @PutMapping("/new_bid")
    public AuctionResponse createNewBid(@RequestBody BidRequest bidRequest)
            throws NotEnoughtMoneyException, SlaveNotExistException, AuctionNotFoundException {
        return auctionService.updateAuction(bidRequest);
    }

    @DeleteMapping("/{id}")
    public String deleteAuction(@PathVariable("id") Long auctionId)
            throws AuctionNotFoundException {
        return auctionService.deleteAuction(auctionId);
    }

    @DeleteMapping("/{id}/finish")
    public String finishAuction(@PathVariable("id") Long auctionId)
            throws AuctionNotFoundException {
        return auctionService.finishAuction(auctionId);
    }

}
