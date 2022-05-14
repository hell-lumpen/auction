package com.example.auctionback.controllers.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = false)
@AllArgsConstructor
public class LoggingParams {
    private String nickname;
    private String password;

}
