package com.sparos.uniquone.msapostservice.review.repository;

import com.sparos.uniquone.msapostservice.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReviewRepository extends JpaRepository<Review, Long> {
}
