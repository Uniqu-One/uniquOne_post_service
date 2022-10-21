package com.sparos.uniquone.msapostservice.post.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.post.dto.response.search.PostSearchTitleResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparos.uniquone.msapostservice.post.domain.QPost.post;
import static com.sparos.uniquone.msapostservice.post.domain.QPostImg.postImg;

@Slf4j
@Repository
public class SearchPostRepositoryImpl extends QuerydslRepositorySupport implements SearchPostRepository {

    private final JPAQueryFactory queryFactory;
    public SearchPostRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Post.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Object[]> searchPostPage(String keyword, Pageable pageable) {

//        QPostImg qPostImg = QPostImg.postImg;
        //해당 키워드가 포함된 포스트 + 포스트 이미지 1번.
        List<PostSearchTitleResponseDto> result = queryFactory
                .select(Projections.fields(PostSearchTitleResponseDto.class
                        , post.id.as("postId")
                        , postImg.url.as("imgUrl")))
                .from(post)
                .leftJoin(postImg.post, post).on(postImg.idx.eq(1))
                .where(searchPostTitle(keyword))
                .fetch();


        for (PostSearchTitleResponseDto dto : result) {
            System.out.println("dto = " + dto);
        }

        return null;

    }

    public BooleanExpression searchPostTitle(String keyword){
        return keyword != null ? post.title.contains(keyword) : null;
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
