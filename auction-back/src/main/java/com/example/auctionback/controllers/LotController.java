package com.example.auctionback.controllers;

import com.example.auctionback.controllers.models.OrderDTO;
import com.example.auctionback.controllers.models.LotDTO;
import com.example.auctionback.database.entities.Lot;
import com.example.auctionback.database.entities.Order;
import com.example.auctionback.exceptions.*;
import com.example.auctionback.security.models.OurAuthToken;
import com.example.auctionback.services.LotService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
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
        return lotService.getLot(auctionId);
    }
// todo: у всех гетов не заполняется ownerId
    @GetMapping("/private/auction/{id}/orders")
    public List<OrderDTO> getAllOrder(@PathVariable("id") Long auctionId) throws LotNotFoundException {
        return lotService.getAllOrders(auctionId);
    }

    @GetMapping("/public/auction/all")
    public List<LotDTO> getAllAuctions() {
        return lotService.getAllLots();
    }

    @PutMapping("/private/auction/{id}/bid")
    public OrderDTO createNewBid(@PathVariable("id") Long auctionId, @RequestBody OrderDTO bidRequest)
            throws NotEnoughtMoneyException, BidderNotFoundException, LotNotFoundException {
        return lotService.updateAuction(bidRequest);
    }

//    @DeleteMapping("/{id}")
//    public String deleteAuction(@PathVariable("id") Long auctionId)
//            throws LotNotFoundException {
//        return lotService.deleteAuction(auctionId);
//    }

    @PutMapping("/private/auction/{id}/finish")
    // todo: убрать мапинг. сделать ее приватной с запуском по таймеру
    public String finishAuction(@PathVariable("id") Long auctionId)
            throws LotNotFoundException {
        return lotService.finishAuction(auctionId);
    }


}
