package com.sparos.uniquone.msapostservice.post.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.post.domain.QPost;
import com.sparos.uniquone.msapostservice.post.domain.QPostImg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class SearchPostRepositoryImpl extends QuerydslRepositorySupport implements SearchPostRepository {

    public SearchPostRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Post.class);
    }

    @Override
    public Page<Object[]> searchPostPage(String keyword, Pageable pageable) {
        log.info("testSearch ..");

//        List<Tuple> fetch = query.select(post, postImg.url)
//                .leftJoin(postImg).on(postImg.post.eq(post))
//                .from(post)
//                .where(post.title.contains(keyword))
//                .orderBy(post.modDate.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .groupBy(post)
//                .fetch();
//
//        log.info("result = {} ", fetch);


//        return null;
//        return new PageImpl<>(result, pageable, total);


        QPost post = QPost.post;
        QPostImg postImg = QPostImg.postImg;

        JPQLQuery<Post> jpqlQuery = from(post);
        jpqlQuery.leftJoin(postImg).on(postImg.post.eq(post));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(post,postImg);

        BooleanBuilder booleanBuilder = new BooleanBuilder();
//        BooleanExpression expression = post.id.gt(0L);
//
//        booleanBuilder.and(expression);

        BooleanBuilder conditionBuilder = new BooleanBuilder();
        conditionBuilder.or(post.title.contains(keyword));

        booleanBuilder.and(conditionBuilder);

        tuple.where(booleanBuilder);
        tuple.groupBy(post);
//        this.getQuerydsl().applyPagination(PageRequest.of(0, 1, Sort.by("id").ascending(), tuple));

//        List<Tuple> result = tuple.fetch();
        List<Tuple> result = tuple.fetchJoin().fetch();



        log.info("result = {} ",result);


//        return null;
        return null;
    }


    @Override
    public Slice<Object[]> searchPostPageBySlice(String keyword, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Object[]> searchPostPage(String type, String keyword, Pageable pageable) {
        return null;
    }
}
