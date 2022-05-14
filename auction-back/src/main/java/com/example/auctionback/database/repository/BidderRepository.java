package com.example.auctionback.database.repository;


import com.example.auctionback.database.entities.Bidder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BidderRepository extends CrudRepository<Bidder, Long> {
    Optional<Bidder> findOptionalByNickname(String nickname);
    Optional<Bidder> findOptionalByNicknameAndPassword(String nickname, String password);

}

