package com.sparos.uniquone.msapostservice.noti.repository;

import com.sparos.uniquone.msapostservice.noti.domain.Noti;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface INotiRepository extends JpaRepository<Noti, Long> {

    List<Noti> findByUserId(Long userId, Pageable pageable);

    Optional<Noti> findByIdAndUserId(Long notiId, Long userId);

    List<Noti> findByUserId(Long userId);

    Long countByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE Noti SET isCheck = true WHERE userId =:userId AND isCheck = false")
    void updateIsCheckByUserId(@Param("userId") Long userId);
}
