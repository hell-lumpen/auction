package com.example.auctionback.controllers.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationParams {
    private String name;
    private String money;

    private String nickname;
    private String password;
}
