package com.example.auctionback.services;

import com.example.auctionback.controllers.models.BidderDTO;
import com.example.auctionback.controllers.models.LoggingParams;
import com.example.auctionback.controllers.models.RegistrationParams;
import com.example.auctionback.exceptions.BidderAlreadyExistException;
import com.example.auctionback.exceptions.BidderNotFoundException;
import com.example.auctionback.exceptions.DataNotCorrectException;
import org.springframework.http.ResponseEntity;

public interface RegistrationService {

    BidderDTO signup(RegistrationParams params) throws BidderAlreadyExistException;


    ResponseEntity<String> signin(LoggingParams params) throws BidderNotFoundException, DataNotCorrectException;
}
