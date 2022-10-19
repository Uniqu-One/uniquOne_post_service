package com.sparos.uniquone.msapostservice.qna.repository;

import com.sparos.uniquone.msapostservice.qna.domain.QnA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IQnARepository extends JpaRepository<QnA, Long> {

    List<QnA> findByUserId(Long userId);

    Optional<QnA> findByIdAndUserId(Long qnaId, Long userId);
}
