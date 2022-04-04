package com.example.auctionback.controllers;

import com.example.auctionback.controllers.models.BidDTO;
import com.example.auctionback.controllers.models.LotDTO;
import com.example.auctionback.exceptions.*;
import com.example.auctionback.services.LotService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
@AllArgsConstructor
public class LotController {
    private final LotService lotService;

    @PostMapping("/auction")
    public LotDTO createAuction(@RequestBody LotDTO auctionRequest)
            throws LotAlreadyExistException, ItemNotFoundException {
        return lotService.createNewAuction(auctionRequest);
    }

    @GetMapping("/auction/{id}")
    public LotDTO getAuction(@PathVariable("id") Long auctionId) throws LotNotFoundException {
        return lotService.getAuction(auctionId);
    }

    @GetMapping("/all")
    public List<LotDTO> getAllAuctions() {
        return lotService.getAllAuctions();
    }

    @PutMapping("/{id}/bid")
    public LotDTO createNewBid(@PathVariable("id") Long auctionId, @RequestBody BidDTO bidRequest)
            throws NotEnoughtMoneyException, BidderNotFoundException, LotNotFoundException {
        return lotService.updateAuction(bidRequest);
    }

    @DeleteMapping("/{id}")
    public String deleteAuction(@PathVariable("id") Long auctionId)
            throws LotNotFoundException {
        return lotService.deleteAuction(auctionId);
    }

    @PutMapping("/{id}/finish")
    // todo: убрать мапинг. сделать ее приватной с запуском по таймеру
    public String finishAuction(@PathVariable("id") Long auctionId)
            throws LotNotFoundException {
        return lotService.finishAuction(auctionId);
    }

}
