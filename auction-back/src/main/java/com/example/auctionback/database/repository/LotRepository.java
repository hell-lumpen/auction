package com.example.auctionback.database.repository;

import com.example.auctionback.database.entities.Lot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LotRepository extends CrudRepository<Lot, Long>, PagingAndSortingRepository<Lot, Long> {
    Optional<Long> findByItemId(Long id);
    Optional<Long> findByOwnerNickname(String nickname);
    List<Lot> findAllByLotStatus(boolean lotStatus);
    Boolean existsByItemId(Long id);
}
