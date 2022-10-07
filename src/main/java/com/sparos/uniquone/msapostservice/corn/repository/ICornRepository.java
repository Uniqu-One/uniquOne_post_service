package com.sparos.uniquone.msapostservice.corn.repository;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICornRepository extends JpaRepository<Corn, Long> {
    Optional<Corn> findByUserId(Long userId);

    Boolean existsByUserId(Long userId);

    Boolean existsByTitle(String title);
}
