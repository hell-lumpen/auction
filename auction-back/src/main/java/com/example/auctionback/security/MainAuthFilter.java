package com.example.auctionback.security;

import com.example.auctionback.database.entities.Bidder;
import com.example.auctionback.database.repository.BidderRepository;
import com.example.auctionback.security.models.OurAuthToken;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Component
public class MainAuthFilter implements Filter {
    private static final String LOGIN_HEADER = "x-access-login";
    private static final String PASSWORD_HEADER = "x-access-password";

    protected final AuthenticationFailureHandler failureHandler;
    private final BidderRepository bidderRepository;
    private final PasswordEncoder passwordEncoder;


    private RequestMatcher requireAuthMatcher;

    public MainAuthFilter setRequireAuthMatcher(RequestMatcher requireAuthMatcher) {
        this.requireAuthMatcher = requireAuthMatcher;
        return this;
    }

    public MainAuthFilter(AuthenticationFailureHandler failureHandler,
                          BidderRepository bidderRepository,
                          PasswordEncoder passwordEncoder) {
        this.failureHandler = failureHandler;
        this.bidderRepository = bidderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        OurAuthToken token = tryAuth((HttpServletRequest) req, (HttpServletResponse) res);
        if (requireAuth((HttpServletRequest) req)) {
            if (token == null) {
                failureHandler.onAuthenticationFailure(
                        (HttpServletRequest) req,
                        (HttpServletResponse) res,
                        new AuthenticationServiceException("Invalid nickname or password"));
            } else {
                SecurityContextHolder.getContext().setAuthentication(token);
                chain.doFilter(req, res);
            }
        }else{
            chain.doFilter(req, res);
        }
    }

    @Nullable
    protected OurAuthToken tryAuth(HttpServletRequest req,
                                   HttpServletResponse res){

        String login = req.getHeader(LOGIN_HEADER);
        String password = req.getHeader(PASSWORD_HEADER);
        String passwordHash = passwordEncoder.encode(password + "sada");
        Optional<Bidder> optionalBidder = bidderRepository.findOptionalByNickname(login);

        if (optionalBidder.isEmpty()){
            return null;
        }

        Bidder bidder = optionalBidder.get();

        if (passwordEncoder.matches(password+"sada", bidder.getPassword())){
            return null;
        }

        Collection<? extends GrantedAuthority> authorities = Collections.singleton(
                new SimpleGrantedAuthority("BASE_USER")
        );

        return new OurAuthToken(bidder.getId(), bidder, authorities);
    }


    private boolean requireAuth(HttpServletRequest req) {
        return requireAuthMatcher == null || requireAuthMatcher.matches(req);
    }
}
