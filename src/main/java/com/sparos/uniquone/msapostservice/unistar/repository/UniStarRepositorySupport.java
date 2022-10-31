package com.sparos.uniquone.msapostservice.unistar.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparos.uniquone.msapostservice.post.domain.QPost;
import com.sparos.uniquone.msapostservice.post.domain.QPostImg;
import com.sparos.uniquone.msapostservice.unistar.domain.QUniStar;
import com.sparos.uniquone.msapostservice.unistar.domain.UniStar;
import com.sparos.uniquone.msapostservice.unistar.dto.response.UniStarGetPostListResponseDto;
import com.sparos.uniquone.msapostservice.unistar.dto.response.UniStarGetPostResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.sparos.uniquone.msapostservice.post.domain.QPost.post;
import static com.sparos.uniquone.msapostservice.post.domain.QPostImg.postImg;
import static com.sparos.uniquone.msapostservice.unistar.domain.QUniStar.uniStar;

@Repository
public class UniStarRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public UniStarRepositorySupport(JPAQueryFactory queryFactory) {
        super(UniStar.class);
        this.queryFactory = queryFactory;
    }


    //내가 찍은 유니스타 포스트 조회.
    public UniStarGetPostListResponseDto getPostListOfMyUniStar(Long userPkId, Integer uniStarLevel, Pageable pageable) {

//        class java.lang.Long, class java.lang.Long, class java.lang.String, class java.lang.Integer]
        List<UniStarGetPostResponseDto> result = queryFactory.select(Projections.constructor(UniStarGetPostResponseDto.class
                                , post.id.as("postId")
                                , uniStar.id.as("uniStarId")
                                , postImg.url.as("postImgUrl")
                                , uniStar.level.as("uniStarLevel")
                        )
                )
                .from(post)
                .leftJoin(postImg).on(post.eq(postImg.post))
                .leftJoin(uniStar).on(post.eq(uniStar.post).and(uniStar.userId.eq(userPkId)))
                .where(searchPostImgIdxEq()
                        ,uniStar.userId.eq(userPkId)
                        ,uniStarLevelEq(uniStarLevel))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(new OrderSpecifier<>(Order.DESC, uniStar.modDate))
                .fetch();

        boolean hasNext = false;
        if(result.size() > pageable.getPageSize()){
            hasNext = true;
            result.remove(pageable.getPageSize());
        }

        return new UniStarGetPostListResponseDto(result,hasNext);
    }

    public BooleanExpression searchPostImgIdxEq() {
        return postImg.idx.eq(1);
    }

    public BooleanExpression uniStarLevelEq(Integer uniStarLevel) {
        return uniStarLevel != null ? uniStar.level.eq(uniStarLevel) : null;
    }
}
