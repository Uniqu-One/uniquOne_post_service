package com.sparos.uniquone.msapostservice.look.repository;

import com.sparos.uniquone.msapostservice.look.domain.Look;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILookRepository extends JpaRepository<Look, Long> {
}
