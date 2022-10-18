package com.sparos.uniquone.msapostservice.trade.repository;

import com.sparos.uniquone.msapostservice.trade.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface ITradeRepository extends JpaRepository<Trade, Long> {

    Optional<Trade> findByIdAndIsReview(Long tradeId, Boolean isReview);

}
