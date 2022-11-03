package com.sparos.uniquone.msapostservice.util.complex.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparos.uniquone.msapostservice.look.domain.Look;
import com.sparos.uniquone.msapostservice.util.complex.dto.MainContentsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.sparos.uniquone.msapostservice.cool.domain.QCool.cool;
import static com.sparos.uniquone.msapostservice.corn.domain.QCorn.corn;
import static com.sparos.uniquone.msapostservice.look.domain.QLook.look;
import static com.sparos.uniquone.msapostservice.post.domain.QPost.post;
import static com.sparos.uniquone.msapostservice.post.domain.QPostAndLook.postAndLook;
import static com.sparos.uniquone.msapostservice.unistar.domain.QUniStar.uniStar;

@RequiredArgsConstructor
@Repository
public class ComplexRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public List<MainContentsDto> findByCornIdMainFollowContentsList(List<Long> cornIdList,Long userId, Pageable pageable){
        List<MainContentsDto> MainContentsDtoList;
        MainContentsDtoList = (List<MainContentsDto>) jpaQueryFactory
                .select(Projections.fields(MainContentsDto.class,
                        post.id.as("postId"),
                        corn.id.as("cornId"),
                        corn.userId.as("userId"),
                        corn.title.as("cornTitle"),
                        corn.imgUrl.as("cornImgUrl"),
                        post.regDate.as("regDate"),
                        uniStar.level.as("uniStar")))
                .from(corn)
                .leftJoin(post).on(post.corn.eq(corn))
                .leftJoin(uniStar).on(uniStar.post.eq(post).and(uniStar.userId.eq(userId)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .where(corn.id.in(cornIdList))
                .orderBy(post.regDate.asc()).fetch();
        return MainContentsDtoList;
    }

    public List<MainContentsDto> findByStyleIdListMainContentsList(List<Long> lookIdList,Long userId ,Pageable pageable){
        List<MainContentsDto> MainContentsDtoList;
        MainContentsDtoList = (List<MainContentsDto>) jpaQueryFactory
                .select(Projections.fields(MainContentsDto.class,
                        post.id.as("postId"),
                        corn.id.as("cornId"),
                        corn.userId.as("userId"),
                        corn.title.as("cornTitle"),
                        corn.imgUrl.as("cornImgUrl"),
                        post.regDate.as("regDate"),
                        uniStar.level.as("uniStar")))
                .from(postAndLook)
                .innerJoin(post).on(post.eq(postAndLook.post))
                .leftJoin(uniStar).on(uniStar.post.eq(post).and(uniStar.userId.eq(userId)))
                .leftJoin(corn).on(corn.eq(post.corn))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .where(postAndLook.look.id.in(lookIdList)).groupBy(post).orderBy(post.regDate.asc()).fetch();
        return MainContentsDtoList;
    }

    public List<MainContentsDto> findCoolDESCListMainContentsList(Long userId,Pageable pageable){
        List<MainContentsDto> MainContentsDtoList;
        MainContentsDtoList = (List<MainContentsDto>) jpaQueryFactory
                .select(Projections.fields(MainContentsDto.class,
                        post.id.as("postId"),
                        corn.id.as("cornId"),
                        corn.userId.as("userId"),
                        corn.title.as("cornTitle"),
                        corn.imgUrl.as("cornImgUrl"),
                        post.regDate.as("regDate"),
                        uniStar.level.as("uniStar")))
                .from(post)
                .leftJoin(corn).on(corn.eq(post.corn))
                .leftJoin(uniStar).on(uniStar.post.eq(post).and(uniStar.userId.eq(userId)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .orderBy(post.regDate.asc()).fetch();
        return MainContentsDtoList;
    }

    public List<Long> findByUserIdCoolStyle(Long userId){
        List<Long> userCoolStyle = jpaQueryFactory
                .select(postAndLook.look.id)
                .from(cool)
                .leftJoin(post).on(post.eq(cool.post))
                .rightJoin(postAndLook).on(postAndLook.post.eq(post))
                .where(cool.userId.eq(userId)).groupBy(postAndLook.look.id).orderBy(postAndLook.look.id.count().desc())
                .fetch().stream().limit(3).collect(Collectors.toList());
        return userCoolStyle;
    }
}
