package com.example.auctionback.security.models;

import com.example.auctionback.database.entities.Bidder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Setter
@Getter
public class OurAuthToken extends AbstractAuthenticationToken {

    private Bidder principal;
    private String bidderNickname;


    public OurAuthToken(String bidderNickname, Bidder principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.bidderNickname = bidderNickname;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

}
