package com.sparos.uniquone.msapostservice.eco.repository;

import com.sparos.uniquone.msapostservice.eco.domain.Eco;
import com.sparos.uniquone.msapostservice.eco.dto.EcoSumDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface IEcoRepository extends JpaRepository<Eco, Long> {

    Optional<Eco> findByRegDate(String regDate);

    @Query(value = "SELECT sum(e.carbon) FROM Eco as e")
    Double findCarbonSumEco();

    @Query(value = "SELECT sum(e.distance) FROM Eco as e")
    Double findDistanceSumEco();

    @Query(value = "SELECT sum(e.water) FROM Eco as e")
    Double findWaterSumEco();

}
