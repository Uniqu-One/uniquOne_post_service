package com.sparos.uniquone.msapostservice.follow.repository;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.follow.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface IFollowRepository extends JpaRepository<Follow, Long> {
    Optional<Long> countByUserId(Long userId);
    Optional<Long> countByCorn(Corn corn);
    Boolean existsByUserIdAndCornId(Long userId, Long cornId);
    @Transactional
    Optional<Long>  deleteByUserIdAndCornId(Long userId, Long cornId);
    List<Follow> findByUserId(Long userId);
    List<Follow> findByCornId(Long cornId);
    List<Follow> findByCorn(Corn corn);
    Optional<Follow> findByUserIdAndCornId(Long userId, Long cornId);
}
