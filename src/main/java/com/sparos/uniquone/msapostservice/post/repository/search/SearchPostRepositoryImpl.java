package com.sparos.uniquone.msapostservice.post.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.post.domain.QPost;
import com.sparos.uniquone.msapostservice.post.domain.QPostImg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Slf4j
public class SearchPostRepositoryImpl extends QuerydslRepositorySupport implements SearchPostRepository {

    public SearchPostRepositoryImpl(Class<?> domainClass) {
        super(Post.class);
    }

    @Override
    public Page<Object[]> searchPostPage(String keyword, Pageable pageable) {
        log.info("testSearch ..");

        QPost post = QPost.post;
        JPQLQuery<Post> jpqlQuery = from(post);

        JPQLQuery<Tuple> tuple = jpqlQuery.select(post, post.count());

        BooleanBuilder conditionBuilder = new BooleanBuilder();
//        conditionBuilder.or(post.)

//        tuple.where()

//        jpqlQuery.select(post).where(post.id.eq(1L));

        log.info("-------------------------");
        log.info("test = {}",jpqlQuery);
        log.info("-------------------------");

        List<Post> result = jpqlQuery.fetch();

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
