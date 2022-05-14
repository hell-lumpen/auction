package com.example.auctionback.controllers;

import com.example.auctionback.controllers.models.ItemDTO;
import com.example.auctionback.controllers.models.BidderDTO;
import com.example.auctionback.database.entities.Bidder;
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

    @GetMapping("/{nickname}")
    public BidderDTO getBidder(@PathVariable("nickname") String clientNickname)
            throws BidderNotFoundException {
        //todo: get all items
        return bidderService.getBidder(clientNickname);
    }

    @GetMapping("/all")
    // пока не нужен, но пусть будет
    public List<BidderDTO> getBidders()  {
        return bidderService.getAllBidders();
    }

    @PostMapping("/logout")
    public BidderDTO bidderLogOut(@RequestBody BidderDTO bidderDTO) {
//        todo: реализовать logOut
        return null;
    }

    @PostMapping("/{nickname}/items")
    public ItemDTO addItem(@PathVariable("nickname") String bidderNickname, @RequestBody ItemDTO itemRequest)
            throws ItemAlreadyExistException, BidderNotFoundException {

        return bidderService.addItem(bidderNickname, itemRequest);
    }

    @GetMapping("/{nickname}/items")
    public List<ItemDTO> getAllItems(@PathVariable("nickname") String bidderNickname)
            throws ItemNotFoundException, BidderNotFoundException {

        return bidderService.getAllBidderItems(bidderNickname);
    }

    @DeleteMapping("/{id}")
    public BidderDTO deleteBidder(@PathVariable("id") String bidderNickname)
            throws BidderNotFoundException {
        //todo: delete items this user
        return bidderService.deleteBidder(bidderNickname);
    }

    private BidderDTO convertToDTO(Bidder bidder) {
        return new ModelMapper().map(bidder, BidderDTO.class);
    }

    private Bidder convertToEntity(BidderDTO bidderDTO) {
        return new ModelMapper().map(bidderDTO, Bidder.class);
    }
}
