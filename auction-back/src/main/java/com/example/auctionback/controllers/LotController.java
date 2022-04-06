package com.example.auctionback.controllers;

import com.example.auctionback.controllers.models.OrderDTO;
import com.example.auctionback.controllers.models.LotDTO;
import com.example.auctionback.database.entities.Lot;
import com.example.auctionback.database.entities.Order;
import com.example.auctionback.exceptions.*;
import com.example.auctionback.services.LotService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
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
        return lotService.createNewLot(auctionRequest);
    }

    @GetMapping("/auction/{id}")
    public LotDTO getAuction(@PathVariable("id") Long auctionId) throws LotNotFoundException {
        return lotService.getLot(auctionId);
    }

    @GetMapping("/all")
    public List<LotDTO> getAllAuctions() {
        return lotService.getAllLots();
    }

    @PutMapping("/{id}/bid")
    public LotDTO createNewBid(@PathVariable("id") Long auctionId, @RequestBody OrderDTO bidRequest)
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

    private LotDTO convertToDTO(Lot lot) {
        return new ModelMapper().map(lot, LotDTO.class);
    }

    private Lot convertToEntity(LotDTO lotDTO) {
        return new ModelMapper().map(lotDTO, Lot.class);
    }

}
