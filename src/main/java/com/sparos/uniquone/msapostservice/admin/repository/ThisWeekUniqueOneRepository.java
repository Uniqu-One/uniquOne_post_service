package com.sparos.uniquone.msapostservice.admin.repository;

import com.sparos.uniquone.msapostservice.admin.domain.ThisWeekUniqueOne;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThisWeekUniqueOneRepository extends JpaRepository<ThisWeekUniqueOne, Long> {
}
