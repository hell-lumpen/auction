package com.example.auctionback.controllers;


import com.example.auctionback.controllers.models.BidderDTO;
import com.example.auctionback.controllers.models.LoggingParams;
import com.example.auctionback.controllers.models.RegistrationParams;
import com.example.auctionback.exceptions.BidderAlreadyExistException;
import com.example.auctionback.exceptions.BidderNotFoundException;
import com.example.auctionback.exceptions.DataNotCorrectException;
import com.example.auctionback.services.RegistrationService;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/signup")
    public BidderDTO signup(@RequestBody RegistrationParams params) throws BidderAlreadyExistException {
        return registrationService.signup(params);
    }

    @GetMapping("/signin")
    public String sin(){
        return ("<!DOCTYPE HTML>\n" +
                "<html xmlns=\"http://www.w3.org/1999/html\" lang=\"\">\n" +
                " <body>\n" +
                "<form method=\"post\">\n" +
                "    Pidor <br>\n" +
                "    Login:\n" +
                "    <label>\n" +
                "        <input name=\"nickname\" id=\"nickname\" type=\"text\" value=\"\">\n" +
                "    </label>\n" +
                "    Password:\n" +
                "    <label>\n" +
                "        <input name=\"password\" id=\"password\" type=\"text\" value=\"\">\n" +
                "    </label>\n" +
                "\n" +
                "    <input type=\"submit\" name=\"send\" value=\"Sign in\">\n" +
                "</form>\n" +
                " </body>\n" +
                "</html>");
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody LoggingParams params) throws BidderAlreadyExistException, DataNotCorrectException, BidderNotFoundException {
//        LoggingParams params = new LoggingParams(nickname, password);

        return registrationService.signin(params);
    }
}
