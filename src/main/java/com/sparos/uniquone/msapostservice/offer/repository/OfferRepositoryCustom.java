package com.sparos.uniquone.msapostservice.offer.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparos.uniquone.msapostservice.offer.domain.OfferType;
import com.sparos.uniquone.msapostservice.offer.dto.OfferCntDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.sparos.uniquone.msapostservice.offer.domain.QOffer.offer;
import static com.sparos.uniquone.msapostservice.post.domain.QPost.post;

@RequiredArgsConstructor
@Repository
@Log4j2
public class OfferRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public List<OfferCntDto> findCntByPostIdIn(List<Long> postIds){

        return jpaQueryFactory
                .select(Projections.fields(OfferCntDto.class,
                        offer.post.id.as("postId"),
                        offer.post.title.as("postTitle"),
                        offer.price.as("price"),
                        offer.waitingCnt.as("waitingCnt"),
                        offer.acceptCount.as("acceptCount"),
                        offer.refuseCount.as("refuseCount")))
                .from(offer)
                .where(offer.post.id.in(postIds))
                .groupBy(offer.post.id)
                .fetch();
    }

    public Optional<OfferCntDto> findCntByPostId(Long postId){

        return Optional.ofNullable(jpaQueryFactory
                .select(Projections.fields(OfferCntDto.class,
                        offer.post.id.as("postId"),
                        offer.price.as("price"),
                        offer.waitingCnt.as("waitingCnt"),
                        offer.acceptCount.as("acceptCount"),
                        offer.refuseCount.as("refuseCount")))
                .from(offer)
                .where(offer.post.id.eq(postId))
                .groupBy(offer.post.id)
                .fetchOne());
    }

}
