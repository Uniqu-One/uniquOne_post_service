package com.sparos.uniquone.msapostservice.admin.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparos.uniquone.msapostservice.admin.domain.QThisWeekUniqueOne;
import com.sparos.uniquone.msapostservice.admin.domain.ThisWeekUniqueOne;
import com.sparos.uniquone.msapostservice.admin.dto.response.ThisWeekUniqueOneResponseDto;
import com.sparos.uniquone.msapostservice.corn.domain.QCorn;
import com.sparos.uniquone.msapostservice.post.domain.QPost;
import com.sparos.uniquone.msapostservice.post.domain.QPostImg;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparos.uniquone.msapostservice.admin.domain.QThisWeekUniqueOne.thisWeekUniqueOne;
import static com.sparos.uniquone.msapostservice.corn.domain.QCorn.corn;
import static com.sparos.uniquone.msapostservice.post.domain.QPost.post;
import static com.sparos.uniquone.msapostservice.post.domain.QPostImg.postImg;

@Repository
public class ThisWeekUniqueOneSupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public ThisWeekUniqueOneSupport(JPAQueryFactory queryFactory) {
        super(ThisWeekUniqueOne.class);
        this.queryFactory = queryFactory;
    }

    public List<ThisWeekUniqueOneResponseDto> getUniqueOneInfo() {

        QPost subPost = new QPost("subPost");
        QPostImg subPostImg = new QPostImg("subPostImg");

        return queryFactory.select(
                        Projections.constructor(ThisWeekUniqueOneResponseDto.class
                                , corn.id.as("cornId")
                                , corn.imgUrl.as("cornImgUrl")
                                , corn.userNickName.as("userNickName")
                                , postImg.url.as("postImgUrl")
                        )
                ).from(thisWeekUniqueOne)
                .leftJoin(corn).on(thisWeekUniqueOne.cornId.eq(corn.id))
                .leftJoin(post).on(post.id.eq(JPAExpressions.select(subPost.id.max())
                        .from(subPost)
                        .where(corn.id.eq(subPost.corn.id))))
//                .leftJoin(post).on(corn.id.eq(new SQLSubQuery)
                .leftJoin(postImg).on(postImg.post.id.eq(post.id))
                .leftJoin(postImg).on(postImg.post.id.eq(post.id))
                .limit(5)
                .where(
                        postImg.idx.eq(1)
                )
                .orderBy(thisWeekUniqueOne.regDate.desc())
                .fetch();
    }

}
