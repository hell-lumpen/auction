package com.example.auctionback.services.impl;

import com.example.auctionback.controllers.models.BidderDTO;
import com.example.auctionback.controllers.models.LoggingParams;
import com.example.auctionback.controllers.models.RegistrationParams;
import com.example.auctionback.database.entities.Bidder;
import com.example.auctionback.database.repository.BidderRepository;
import com.example.auctionback.exceptions.BidderAlreadyExistException;
import com.example.auctionback.exceptions.BidderNotFoundException;
import com.example.auctionback.exceptions.DataNotCorrectException;
import com.example.auctionback.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;



@Service
public class RegistrationServiceImpl implements RegistrationService {
    private static final String LOGIN_HEADER = "x-access-login";
    private static final String PASSWORD_HEADER = "x-access-password";
    private final BidderRepository bidderRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private HttpServletResponse res;

    public RegistrationServiceImpl(BidderRepository bidderRepository, PasswordEncoder passwordEncoder) {
        this.bidderRepository = bidderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public BidderDTO signup(RegistrationParams params) throws BidderAlreadyExistException {
        Optional<Bidder> existedBidder = bidderRepository.findOptionalByNickname(params.getNickname());
        if (existedBidder.isPresent()) {
            throw new BidderAlreadyExistException();
        }
        Bidder bidder = Bidder.builder()
                .name(params.getName())
                .money(params.getMoney())
                .reservedMoney("0")
                .nickname(params.getNickname())
                .password(params.getPassword())
                .build();


        String password = passwordEncoder.encode(bidder.getPassword() + "sada");
        bidder.setPassword(password);
        bidderRepository.save(bidder);
        return(BidderDTO.builder()
                .name(bidder.getName())
                .money(bidder.getMoney())
                .reservedMoney("0")
                .nickname(bidder.getNickname())
                .password(bidder.getPassword())
                .build());
    }

    @Override
    public ResponseEntity<String> signin(LoggingParams params) throws BidderNotFoundException, DataNotCorrectException {
        Bidder existedBidder = bidderRepository.findOptionalByNickname(params.getNickname())
                .orElseThrow(BidderNotFoundException::new);
        String passwordHash = passwordEncoder.encode(params.getPassword() + "sada");

        if (!passwordEncoder.matches(params.getPassword() + "sada", existedBidder.getPassword())){
            throw new DataNotCorrectException();
        }

        res.setHeader(LOGIN_HEADER, existedBidder.getNickname());
        res.setHeader(PASSWORD_HEADER, passwordHash);
        HttpHeaders headers = new HttpHeaders();
        headers.add(LOGIN_HEADER, existedBidder.getNickname());
        headers.add(PASSWORD_HEADER, passwordHash);
        return new ResponseEntity("Login", headers, HttpStatus.OK);



        //        return(BidderDTO.builder()
//                .name(existedBidder.getName())
//                .money(existedBidder.getMoney())
//                .reservedMoney(existedBidder.getReservedMoney())
//                .nickname(existedBidder.getNickname())
//                .password(existedBidder.getPassword())
//                .build());
    }

}
