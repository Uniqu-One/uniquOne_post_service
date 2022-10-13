package com.sparos.uniquone.msapostservice.cool.repository;

import com.sparos.uniquone.msapostservice.cool.domain.Cool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ICoolRepository extends JpaRepository<Cool, Long> {
    @Transactional
    void deleteByUserIdAndPostId(Long userId,Long postId);
}
