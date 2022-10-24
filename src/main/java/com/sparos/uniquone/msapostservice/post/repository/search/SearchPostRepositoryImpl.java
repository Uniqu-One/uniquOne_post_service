package com.sparos.uniquone.msapostservice.post.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.post.domain.QPostTag;
import com.sparos.uniquone.msapostservice.post.dto.response.search.PostSearchTitleResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparos.uniquone.msapostservice.cool.domain.QCool.cool;
import static com.sparos.uniquone.msapostservice.post.domain.QPost.post;
import static com.sparos.uniquone.msapostservice.post.domain.QPostImg.postImg;
import static com.sparos.uniquone.msapostservice.post.domain.QPostTag.postTag;

@Slf4j
@Repository
public class SearchPostRepositoryImpl extends QuerydslRepositorySupport implements SearchPostRepository {

    private final JPAQueryFactory queryFactory;

    public SearchPostRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Post.class);
        this.queryFactory = queryFactory;
    }

    //**헷갈리면 봐라 LEFT JOIN -> [ A left join B on (a.id = b.id) ]
    //A(왼쪽) 테이블과 B(오른쪽) 테이블을 조인을 걸건데,
    // B테이블에 A테이블과 맵핑되는 값이 있건 없건 A의 값은 반드시 모두 나오게 됩니다.

    @Override
    public Page<PostSearchTitleResponseDto> searchPostPageOfNonUser(String keyword, Pageable pageable) {

//        QPostImg qPostImg = QPostImg.postImg;
        //해당 키워드가 포함된 포스트 + 포스트 이미지 1번.
        List<PostSearchTitleResponseDto> result = queryFactory
                .select(Projections.fields(PostSearchTitleResponseDto.class
                        , post.id.as("postId")
                        , postImg.url.as("imgUrl")))
                .from(post)
                .leftJoin(postImg).on(post.eq(postImg.post))
                .where(searchPostImgIdxEq()
                        , searchPostTitleContain(keyword))
                .fetch();

        for (PostSearchTitleResponseDto dto : result) {
            System.out.println("dto = " + dto);
        }
        return null;
    }

    @Override
    public Page<PostSearchTitleResponseDto> searchPostPageOfUser(String keyword, Long userPkId, Pageable pageable) {
        //해당 키워드가 포환된 포스트 + 유저가해당 포스트 좋아요 유무 + 포스트 이미지 1번.
        List<PostSearchTitleResponseDto> result = queryFactory
                .select(Projections.constructor(PostSearchTitleResponseDto.class
                        , post.id.as("postId")
                        , postImg.url.as("imgUrl")
                        , cool.id.isNull().as("isCool")
                        )
                )
                .from(post)
                .leftJoin(postImg).on(post.eq(postImg.post))
                .leftJoin(cool).on(post.eq(cool.post))
//                .leftJoin(cool).on(cool.userId.eq(userPkId))
                .where(searchPostImgIdxEq()
                        , searchPostTitleContain(keyword)
                        , searchCoolUserPkIdEq(userPkId)
                )
                .fetch();

        for (PostSearchTitleResponseDto dto : result) {
            System.out.println("dto = " + dto);
        }
        return null;
    }

    @Override
    public Page<PostSearchTitleResponseDto> searchHashTagPageOfNonUser(String keyword, Pageable pageable) {

        //포스트 의 입력된 키워드와 같은 헤쉬태그 이름 + 포스트 이미지 1번.

        List<PostSearchTitleResponseDto> result = queryFactory
                .select(Projections.fields(PostSearchTitleResponseDto.class
                        , post.id.as("postId")
                        , postImg.url.as("imgUrl")
                        )
                )
                .from(post)
                .leftJoin(postImg).on(post.eq(postImg.post))
                .leftJoin(postTag).on(post.eq(postTag.post))
                .where(searchHasTagEq(keyword)
                        , searchPostImgIdxEq())
                .fetch();

        for (PostSearchTitleResponseDto dto : result) {
            System.out.println("dto = " + dto);
        }

        return null;
    }

    @Override
    public Page<PostSearchTitleResponseDto> searchHashTagPageOfUser(String keyword,Long userPkId ,Pageable pageable) {

        //포스트 의 입력된 키워드와 같은 헤쉬태그 이름 + 포스트 이미지 1번 + 유저의 포스트 좋아요 유무.
        List<PostSearchTitleResponseDto> result = queryFactory
                .select(Projections.constructor(PostSearchTitleResponseDto.class
                                , post.id.as("postId")
                                , postImg.url.as("imgUrl")
                                , cool.id.isNull().as("isCool")
                        )
                )
                .from(post)
                .leftJoin(postImg).on(post.eq(postImg.post))
                .leftJoin(postTag).on(post.eq(postTag.post))
                .leftJoin(cool).on(post.eq(cool.post))
                .where(searchHasTagEq(keyword)
                        , searchPostImgIdxEq()
                        , searchCoolUserPkIdEq(userPkId)
                )
                .fetch();

        for (PostSearchTitleResponseDto dto : result) {
            System.out.println("dto = " + dto);
        }
        return null;
    }


    public BooleanExpression searchPostTitleContain(String keyword) {
        return keyword != null ? post.title.contains(keyword) : null;
    }
    public BooleanExpression searchCoolUserPkIdEq(Long userPkId){
        return userPkId != null ? cool.userId.eq(userPkId) : null;
    }

    public BooleanExpression searchPostImgIdxEq() {
        return postImg.idx.eq(1);
    }

    public BooleanExpression searchHasTagEq(String keyword) {
        return keyword != null ? postTag.dsc.eq(keyword) : null;
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
