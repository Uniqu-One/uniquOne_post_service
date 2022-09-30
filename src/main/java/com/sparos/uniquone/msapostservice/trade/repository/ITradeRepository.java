package com.sparos.uniquone.msapostservice.trade.repository;

import com.sparos.uniquone.msapostservice.trade.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITradeRepository extends JpaRepository<Trade, Long> {
}
