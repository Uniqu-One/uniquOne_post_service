package com.sparos.uniquone.msapostservice.look.repository;

import com.sparos.uniquone.msapostservice.look.domain.Look;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ILookRepository extends JpaRepository<Look, Long> {
    Optional<Look> findByName(String name);
}
