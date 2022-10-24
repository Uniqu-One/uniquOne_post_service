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

    public FollowingInfoDto findByUserIdFollowingInfo(Long cornId){
        return (FollowingInfoDto) jpaQueryFactory
                .select(Projections.fields(FollowingInfoDto.class,
                        corn.imgUrl.as("cornImgUrl"),
                        corn.title.as("cornTitle"),
                        corn.id.as("cornId")
                        ))
                .from(corn)
                .where(corn.id.eq(cornId)).fetchOne();
    }
}
