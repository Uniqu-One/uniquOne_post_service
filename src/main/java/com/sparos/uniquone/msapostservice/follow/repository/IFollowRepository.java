package com.sparos.uniquone.msapostservice.follow.repository;

import com.sparos.uniquone.msapostservice.follow.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFollowRepository extends JpaRepository<Follow, Long> {
}
