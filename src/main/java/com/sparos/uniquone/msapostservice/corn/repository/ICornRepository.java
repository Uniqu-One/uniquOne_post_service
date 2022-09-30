package com.sparos.uniquone.msapostservice.corn.repository;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICornRepository extends JpaRepository<Corn, Long> {
}
