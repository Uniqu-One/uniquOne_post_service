package com.sparos.uniquone.msapostservice.corn.repository;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ICornRepository extends JpaRepository<Corn, Long> {
    Optional<Corn> findByUserId(Long userId);

    Boolean existsByUserId(Long userId);

    Boolean existsByTitle(String title);

    @Query("select c.imgUrl from Corn c where c.userId =:userId")
    String findImgUrlByUserId(@Param("userId") Long userId);
}
