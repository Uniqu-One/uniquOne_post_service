package com.sparos.uniquone.msapostservice.corn.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparos.uniquone.msapostservice.cool.domain.QCool;
import com.sparos.uniquone.msapostservice.corn.domain.QCorn;
import com.sparos.uniquone.msapostservice.corn.dto.ReviewStarPostEAInfoOutputDto;
import com.sparos.uniquone.msapostservice.corn.dto.response.CornMyDashboardResponseDto;
import com.sparos.uniquone.msapostservice.follow.domain.QFollow;
import com.sparos.uniquone.msapostservice.offer.domain.QOffer;
import com.sparos.uniquone.msapostservice.post.domain.QPost;
import com.sparos.uniquone.msapostservice.unistar.domain.QUniStar;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.List;

import static com.sparos.uniquone.msapostservice.cool.domain.QCool.cool;
import static com.sparos.uniquone.msapostservice.corn.domain.QCorn.corn;
import static com.sparos.uniquone.msapostservice.follow.domain.QFollow.follow;
import static com.sparos.uniquone.msapostservice.offer.domain.QOffer.offer;
import static com.sparos.uniquone.msapostservice.post.domain.QPost.post;
import static com.sparos.uniquone.msapostservice.review.domain.QReview.review;
import static com.sparos.uniquone.msapostservice.unistar.domain.QUniStar.uniStar;

@RequiredArgsConstructor
@Repository
@Slf4j
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

    public CornMyDashboardResponseDto getMyCornDashboard(Long userId, Long cornId) {

        //follow cornId 개수. 월요일부터 일요일까지.
        Long followCount = jpaQueryFactory.select(follow.count())
                .from(follow)
                .where(follow.corn.id.eq(cornId),
                        follow.regDate.between
                                (getMondayThisWeek(LocalDate.now()).atStartOfDay(),
                                        LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).atStartOfDay()
                                ))
                .fetchOne();

        //콘이 가진 포스트 들의 좋아요 개수.
        Long coolCount = jpaQueryFactory.select(cool.count())
                .from(corn)
                .leftJoin(post).on(post.corn.eq(corn))
                .leftJoin(cool).on(cool.post.eq(post))
                .where(corn.id.eq(cornId),
                        cool.regDate.between(
                                getMondayThisWeek(LocalDate.now()).atStartOfDay(),
                                LocalDate.now().with(DayOfWeek.SUNDAY).atStartOfDay()
                        ))
                .fetchOne();

        //콘이 가진 포스트 들의 유니스타 개수. 레벨은 힘듬 ..ㅎ;
        Long uniStarCount = jpaQueryFactory.select(uniStar.count())
                .from(corn)
                .leftJoin(post).on(post.corn.eq(corn))
                .leftJoin(uniStar).on(uniStar.post.eq(post))
                .where(corn.id.eq(cornId),
                        uniStar.regDate.between(
                                getMondayThisWeek(LocalDate.now()).atStartOfDay(),
                                LocalDate.now().with(DayOfWeek.SUNDAY).atStartOfDay()
                        ))
                .fetchOne();
        //콘이 가진 포스트 들의 오퍼 개수.;
        Long offerCount = jpaQueryFactory.select(offer.count())
                .from(corn)
                .leftJoin(post).on(post.corn.eq(corn))
                .leftJoin(offer).on(offer.post.eq(post))
                .where(corn.id.eq(cornId),
                        offer.regDate.between(
                                getMondayThisWeek(LocalDate.now()).atStartOfDay(),
                                LocalDate.now().with(DayOfWeek.SUNDAY).atStartOfDay()
                        ))
                .fetchOne();


//        log.info("월요일 날짜 = {} ", LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)));
//        log.info("일요일 날짜 = {} ", LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)));
////        log.info("월요일 날짜2 = {} ", LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).atStartOfDay());
//        log.info("월요일 날짜2 = {} ", LocalDate.now().with(DayOfWeek.MONDAY));
//        log.info("일요일 날짜2 = {} ", LocalDate.now().with(DayOfWeek.SUNDAY).atStartOfDay());

//        log.info("월요일 Test = {} ", LocalDate.of(2022, Month.NOVEMBER, 12).with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)));
//        log.info("월요일 Test2 = {} ", getMondayThisWeek(LocalDate.of(2022, Month.NOVEMBER, 12)).atStartOfDay());
//        log.info("일요일 Test = {} ", LocalDate.of(2022, Month.NOVEMBER, 13).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)));
//        log.info("일요일 날짜3 = {} ",LocalDate.of(2022, Month.NOVEMBER, 8).with(DayOfWeek.SUNDAY).atStartOfDay());
        return new CornMyDashboardResponseDto(followCount, coolCount, uniStarCount, offerCount);
    }

    public static LocalDate getMondayThisWeek(LocalDate todayDate) {
        final LocalDate c2;
        //    현재 날자의 요일을 가져오는데 오늘이 일요일 이면 - 6일 뺀다.
        if (todayDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            c2 = todayDate.minusDays(6);
        } else {
            c2 = todayDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        }
        return c2;

    }
}
