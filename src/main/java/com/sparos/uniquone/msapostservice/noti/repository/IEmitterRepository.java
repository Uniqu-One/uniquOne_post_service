package com.sparos.uniquone.msapostservice.noti.repository;

import com.sparos.uniquone.msapostservice.noti.domain.Noti;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INotiRepository extends JpaRepository<Noti, Long> {
}
