package com.sparos.uniquone.msapostservice.admin.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparos.uniquone.msapostservice.admin.domain.ThisSeasonPost;
import com.sparos.uniquone.msapostservice.admin.dto.response.ThisSeasonPostResponseDto;
import com.sparos.uniquone.msapostservice.admin.type.SeasonType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparos.uniquone.msapostservice.admin.domain.QThisSeasonPost.thisSeasonPost;
import static com.sparos.uniquone.msapostservice.post.domain.QPost.post;
import static com.sparos.uniquone.msapostservice.post.domain.QPostImg.postImg;

@Repository
@Slf4j
public class ThisSeasonPostSupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public ThisSeasonPostSupport(JPAQueryFactory queryFactory) {
        super(ThisSeasonPost.class);
        this.queryFactory = queryFactory;
    }


    //계절 별로 조회 5개씩.
    public List<ThisSeasonPostResponseDto> getSeasonPostList(SeasonType seasonType) {

       return queryFactory.select(
                        Projections.constructor(ThisSeasonPostResponseDto.class
                                , post.id.as("postId")
                                , postImg.url.as("postImgUrl")
                        )
                ).from(thisSeasonPost)
                .leftJoin(post).on(thisSeasonPost.postId.eq(post.id))
                .leftJoin(postImg).on(post.id.eq(postImg.post.id))
                .limit(5)
                .where(
                        postImg.idx.eq(1),
                        thisSeasonPost.seasonType.eq(seasonType)
                )
                .orderBy(thisSeasonPost.regDate.desc())
                .fetch();
    }

}
