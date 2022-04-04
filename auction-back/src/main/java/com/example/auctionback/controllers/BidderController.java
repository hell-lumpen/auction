package com.example.auctionback.controllers;

import com.example.auctionback.controllers.models.ItemDTO;
import com.example.auctionback.controllers.models.BidderDTO;
import com.example.auctionback.exceptions.ItemAlreadyExistException;
import com.example.auctionback.exceptions.ItemNotFoundException;
import com.example.auctionback.exceptions.BidderNotFoundException;
import com.example.auctionback.services.BidderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class BidderController {
    private final BidderService bidderService;
//    private final ItemService itemService;

    @GetMapping("/{id}")
    public BidderDTO getSlave(@PathVariable("id") Long slaveId) throws BidderNotFoundException {
        //todo: get all items
        return bidderService.getBidder(slaveId);
    }

    @GetMapping("/all")
    // пока не нужен, но пусть будет для админки например)
    public List<BidderDTO> getSlaves()  {
        return bidderService.getAllBidders();
    }

    @PostMapping("/register") // todo: убрать new, add..
    public BidderDTO slaveRegistration(@RequestBody BidderDTO bidderDTO) {
        return bidderService.saveBidder(bidderDTO);
    }

    @PostMapping("/{id}/item")
    public ItemDTO addItem(@PathVariable("id") Long slaveId, @RequestBody ItemDTO itemRequest)
            throws ItemAlreadyExistException, BidderNotFoundException {
        return bidderService.addItem(slaveId, itemRequest);
    }
    @GetMapping("/{id}/items")
    public List<ItemDTO> getAllItems(@PathVariable("id") Long slaveId)
            throws ItemNotFoundException, BidderNotFoundException {
        return bidderService.getAllBidderItems(slaveId);
    }

    @DeleteMapping("/{id}")
    public BidderDTO deleteSlave(@PathVariable("id") Long slaveId)
            throws BidderNotFoundException {
        //todo: delete items this user
        return bidderService.deleteBidder(slaveId);

    }
}
