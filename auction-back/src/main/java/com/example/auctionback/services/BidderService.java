package com.example.auctionback.services;

import com.example.auctionback.controllers.models.BidderDTO;
import com.example.auctionback.controllers.models.ItemDTO;
import com.example.auctionback.database.entities.Bidder;
import com.example.auctionback.database.entities.Item;
import com.example.auctionback.exceptions.BidderNotFoundException;
import com.example.auctionback.exceptions.DataNotCorrectException;
import com.example.auctionback.exceptions.ItemAlreadyExistException;
import com.example.auctionback.exceptions.ItemNotFoundException;
import com.example.auctionback.security.models.OurAuthToken;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface BidderService {

    public BidderDTO getBidder(String bidderNickname, OurAuthToken token)
            throws BidderNotFoundException, DataNotCorrectException;

    public List<BidderDTO> getAllBidders();

    public List<ItemDTO> getAllBidderItems(String bidderNickname)
            throws ItemNotFoundException, BidderNotFoundException ;

    public ItemDTO addItem(String bidderNickname, ItemDTO itemRequest)
            throws ItemAlreadyExistException, BidderNotFoundException;

    public BidderDTO deleteBidder(String bidderNickname) throws BidderNotFoundException;
}
