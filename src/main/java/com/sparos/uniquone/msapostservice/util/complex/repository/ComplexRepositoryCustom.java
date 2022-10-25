package com.sparos.uniquone.msapostservice.util.complex.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparos.uniquone.msapostservice.util.complex.dto.MainFollowContentsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparos.uniquone.msapostservice.corn.domain.QCorn.corn;
import static com.sparos.uniquone.msapostservice.post.domain.QPost.post;

@RequiredArgsConstructor
@Repository
public class ComplexRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public List<MainFollowContentsDto> findByCornIdMainFollowContentsList(List<Long> cornIdList, Pageable pageable){
        List<MainFollowContentsDto> mainFollowContentsDtoList;
        mainFollowContentsDtoList = (List<MainFollowContentsDto>) jpaQueryFactory
                .select(Projections.fields(MainFollowContentsDto.class,
                        post.id.as("postId"),
                        corn.id.as("cornId"),
                        corn.userId.as("userId"),
                        corn.title.as("cornTitle"),
                        corn.imgUrl.as("cornImgUrl"),
                        post.regDate.as("regDate")))
                .from(corn)
                .innerJoin(post).on(post.corn.eq(corn))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(corn.id.in(cornIdList)).orderBy(post.regDate.asc()).fetch();
        return mainFollowContentsDtoList;
    }

}
