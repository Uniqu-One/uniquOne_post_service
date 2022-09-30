package com.sparos.uniquone.msapostservice.offer.repository;

import com.sparos.uniquone.msapostservice.offer.domain.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOfferRepository extends JpaRepository<Offer, Long> {
}
