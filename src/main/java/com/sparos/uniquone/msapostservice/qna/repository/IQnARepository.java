package com.sparos.uniquone.msapostservice.qna.repository;


import com.sparos.uniquone.msapostservice.qna.domain.QnA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IQnARepository extends JpaRepository<QnA, Long> {

    List<QnA> findByUserId(Long userId);

//    Page<QnA> find(Pageable pageable);

    Optional<QnA> findByIdAndUserId(Long qnaId, Long userId);
}
