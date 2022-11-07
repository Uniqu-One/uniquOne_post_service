package com.sparos.uniquone.msapostservice.corn.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparos.uniquone.msapostservice.corn.dto.ReviewStarPostEAInfoOutputDto;
import com.sparos.uniquone.msapostservice.corn.dto.response.CornMyDashboardResponseDto;
import com.sparos.uniquone.msapostservice.follow.domain.QFollow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static com.sparos.uniquone.msapostservice.follow.domain.QFollow.follow;
import static com.sparos.uniquone.msapostservice.post.domain.QPost.post;
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
                        post.count().as("postEA")
                )).from(post)
                .leftJoin(review).on(review.post.eq(post))
                .where(post.corn.id.eq(cornId)).groupBy(post.corn.id).fetchOne();
    }

    public CornMyDashboardResponseDto getMyCornDashboard(Long userId, Long cornId){

        //follow cornId 개수. 월요일부터 일요일까지.
        jpaQueryFactory.select(follow.count())
                .from(follow)
                .where(follow.corn.id.eq(cornId),
                        follow.regDate.between
                                (LocalDateTime.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)),
                                        LocalDateTime.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)
                                )
                        ))
                .fetch();


        return null;
    }
}
