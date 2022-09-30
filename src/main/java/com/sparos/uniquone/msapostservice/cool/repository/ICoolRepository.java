package com.sparos.uniquone.msapostservice.cool.repository;

import com.sparos.uniquone.msapostservice.cool.domain.Cool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICoolRepository extends JpaRepository<Cool, Long> {
}
