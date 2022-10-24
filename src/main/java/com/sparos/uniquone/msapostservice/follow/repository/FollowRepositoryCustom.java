package com.sparos.uniquone.msapostservice.follow.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparos.uniquone.msapostservice.follow.dto.FollowingInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.sparos.uniquone.msapostservice.corn.domain.QCorn.corn;

@RequiredArgsConstructor
@Repository
public class FollowRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public FollowingInfoDto findByUserIdFollowingInfo(Long userId){
        return (FollowingInfoDto) jpaQueryFactory
                .select(Projections.constructor(FollowingInfoDto.class,
                        corn.id,
                        corn.title,
                        corn.imgUrl))
                .from(corn)
                .where(corn.userId.eq(userId)).fetchOne();
    }
}
