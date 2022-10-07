package com.sparos.uniquone.msapostservice.corn.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparos.uniquone.msapostservice.corn.dto.ReviewStarPostEAInfoOutputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.sparos.uniquone.msapostservice.review.domain.QReview.review;

@RequiredArgsConstructor
@Repository
public class CornRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public ReviewStarPostEAInfoOutputDto findByCornIdPostReview(Long cornId) {
        return (ReviewStarPostEAInfoOutputDto) jpaQueryFactory
                .select(Projections.constructor(ReviewStarPostEAInfoOutputDto.class,
                        review.star.avg().as("reviewStar"),
                        review.count().as("reviewEA"),
                        review.post.count().as("postEA")
                )).from(review)
                .where(review.post.corn.id.eq(cornId)).fetchOne();
    }
}
