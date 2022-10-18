package com.sparos.uniquone.msapostservice.trade.repository;

import com.sparos.uniquone.msapostservice.trade.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface ITradeRepository extends JpaRepository<Trade, Long> {

    Optional<Trade> findByIdAndIsReview(Long tradeId, Boolean isReview);

    List<Trade> findByPostIdIn(List<Long> postId);

    List<Trade> findBySellerId(Long userId);

    List<Trade> findByBuyerId(Long userId);
}
