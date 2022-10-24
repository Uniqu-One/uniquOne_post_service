package com.sparos.uniquone.msapostservice.follow.repository;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.follow.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IFollowRepository extends JpaRepository<Follow, Long> {
    Optional<Long> countByUserId(Long userId);
    Optional<Long> countByCorn(Corn corn);
    Boolean existsByUserIdAndCornId(Long userId, Long cornId);
    Optional<Long>  deleteByUserIdAndCornId(Long userId, Long cornId);
    List<Follow> findByUserId(Long userId);
    List<Follow> findByCorn(Long cornId);
}
