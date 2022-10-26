package com.sparos.uniquone.msapostservice.review.repository;

import com.sparos.uniquone.msapostservice.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByPostIdIn(List<Long> postIds);

    List<Review> findByUserId(Long userId);
}
