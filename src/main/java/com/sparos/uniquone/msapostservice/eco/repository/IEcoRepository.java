package com.sparos.uniquone.msapostservice.eco.repository;

import com.sparos.uniquone.msapostservice.eco.domain.Eco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface IEcoRepository extends JpaRepository<Eco, Long> {

    Optional<Eco> findByRegDate(String regDate);

}
