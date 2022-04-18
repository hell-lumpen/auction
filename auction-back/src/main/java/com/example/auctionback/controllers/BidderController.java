package com.example.auctionback.controllers;

import com.example.auctionback.controllers.models.ItemDTO;
import com.example.auctionback.controllers.models.BidderDTO;
import com.example.auctionback.controllers.models.OrderDTO;
import com.example.auctionback.database.entities.Bidder;
import com.example.auctionback.database.entities.Order;
import com.example.auctionback.exceptions.ItemAlreadyExistException;
import com.example.auctionback.exceptions.ItemNotFoundException;
import com.example.auctionback.exceptions.BidderNotFoundException;
import com.example.auctionback.services.BidderService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class BidderController {
    private final BidderService bidderService;
//    private final ItemService itemService;

    @GetMapping("/{id}")
    public BidderDTO getBidder(@PathVariable("id") Long slaveId) throws BidderNotFoundException {
        //todo: get all items
        return bidderService.getBidder(slaveId);
    }

    @GetMapping("/all")
    // пока не нужен, но пусть будет для админки например)
    public List<BidderDTO> getBidders()  {
        return bidderService.getAllBidders();
    }

    @PostMapping("/signin")
    public BidderDTO bidderSignIn(@RequestBody BidderDTO bidderDTO) {
        return bidderService.saveBidder(bidderDTO);
    }

    @PostMapping("/login")
    public BidderDTO bidderLogIn(@RequestBody BidderDTO bidderDTO) {
//        todo: реализовать logIn
        return null;
    }

    @PostMapping("/logout")
    public BidderDTO bidderLogOut(@RequestBody BidderDTO bidderDTO) {
//        todo: реализовать logOut
        return null;
    }

    @PostMapping("/{id}/items")
    public ItemDTO addItem(@PathVariable("id") Long bidderId, @RequestBody ItemDTO itemRequest)
            throws ItemAlreadyExistException, BidderNotFoundException {
        return bidderService.addItem(bidderId, itemRequest);
    }
    @GetMapping("/{id}/items")
    public List<ItemDTO> getAllItems(@PathVariable("id") Long bidderId)
            throws ItemNotFoundException, BidderNotFoundException {
        return bidderService.getAllBidderItems(bidderId);
    }

    @DeleteMapping("/{id}")
    public BidderDTO deleteBidder(@PathVariable("id") Long bidderId)
            throws BidderNotFoundException {
        //todo: delete items this user
        return bidderService.deleteBidder(bidderId);
    }

    private BidderDTO convertToDTO(Bidder bidder) {
        return new ModelMapper().map(bidder, BidderDTO.class);
    }

    private Bidder convertToEntity(BidderDTO bidderDTO) {
        return new ModelMapper().map(bidderDTO, Bidder.class);
    }
}
