package com.sparos.uniquone.msapostservice.post.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.post.domain.PostType;
import com.sparos.uniquone.msapostservice.post.dto.search.request.SearchRequestDto;
import com.sparos.uniquone.msapostservice.post.dto.search.response.SearchPostListResponseDto;
import com.sparos.uniquone.msapostservice.post.dto.search.response.SearchSingleDto;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.sparos.uniquone.msapostservice.cool.domain.QCool.cool;
import static com.sparos.uniquone.msapostservice.post.domain.QPost.post;
import static com.sparos.uniquone.msapostservice.post.domain.QPostAndLook.postAndLook;
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

    public Slice<SearchSingleDto> fullSearchPostPageOfUser(String keyword, Pageable pageable, HttpServletRequest request) {
        //토큰 꺼냄.
        Long userPkId = JwtProvider.getUserPkId(request);

        //해당 키워드가 포환된 포스트 + 유저가해당 포스트 좋아요 유무 + 포스트 이미지 1번.
        List<SearchSingleDto> result = queryFactory
                .select(Projections.constructor(SearchSingleDto.class
                                , post.id.as("postId")
                                , postImg.url.as("imgUrl")
                                , cool.id.isNull().as("isCool")
                        )
                )
                .from(post)
                .leftJoin(postImg).on(post.eq(postImg.post))
                .leftJoin(cool).on(post.eq(cool.post))
                .where(searchPostImgIdxEq()
                        , searchPostTitleContain(keyword)
                        , searchCoolUserPkIdEq(userPkId)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        return null;
    }

    @Override
    public SearchPostListResponseDto searchPostOfUser(SearchRequestDto searchRequestDto, Long userPkId, Pageable pageable) {

        List<SearchSingleDto> result = queryFactory
                .select(Projections.constructor(SearchSingleDto.class
                                , post.id.as("postId")
                                , postImg.url.as("imgUrl")
                                , cool.userId.eq(userPkId).as("isCool")
                                , post.recommended.as("coolCnt")
                        )
                )
                .from(post)
                .leftJoin(postImg).on(post.eq(postImg.post))
                .leftJoin(cool).on(post.eq(cool.post))
//                .leftJoin(postAndLook).on(post.eq(postAndLook.post))
                .where(
                        searchPostImgIdxEq()
                        //타이틀 키워드
                        , searchPostTitleContain(searchRequestDto.getKeyword())
                        //컬러 리스트
                        , searchPostColorListContains(searchRequestDto.getColors())
                        //카테고리 ID
                        , searchPostCategoryIdEq(searchRequestDto.getCategoryId())
                        //상태 리스트
                        , searchPostConditionListEq(searchRequestDto.getConditions())
                        //포스트 타입(판매중, 세일중)
                        , searchPostTypeListEq(searchRequestDto.getPostType())
                        //스타일 ID
                        , searchPostLookListEq(searchRequestDto.getLooks())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                //오더 조건.
                .fetch();

        //현재 받아온 결과 값의 사이즈 와 Pageable의 size 보다 크면 다음이 있다!.
//        boolean hasNext = false;
//        if(result.size() > pageable.getPageSize()){
//            result.remove(pageable.getPageSize());
//            hasNext = true;
//        }
        //TODO 제대로 동작하는지 체크해봐야됨.
        boolean hasNext = getPageHasNext(result, result.size(), pageable.getPageSize());

        //TODO 전체 검색된 게시물 검색수 필요하면 쿼리 작성

        SearchPostListResponseDto searchPostListResponseDto = new SearchPostListResponseDto(result, hasNext);

        for (SearchSingleDto searchSingleDto : result) {
            log.info("dto = {} ", searchSingleDto);
        }


        return null;
    }

    @Override
    public Page<SearchSingleDto> searchPostOfNonUser(String keyword, Pageable pageable) {


//        QPostImg qPostImg = QPostImg.postImg;
        //해당 키워드가 포함된 포스트 + 포스트 이미지 1번.
        List<SearchSingleDto> result = queryFactory
                .select(Projections.fields(SearchSingleDto.class
                        , post.id.as("postId")
                        , postImg.url.as("imgUrl")))
                .from(post)
                .leftJoin(postImg).on(post.eq(postImg.post))
                .where(searchPostImgIdxEq()
                        , searchPostTitleContain(keyword))
                .fetch();

        for (SearchSingleDto dto : result) {
            System.out.println("dto = " + dto);
        }
        return null;
    }

    @Override
    public SearchPostListResponseDto searchHashTagOfUser(String keyword, Long userPkId, Pageable pageable) {

        //포스트 의 입력된 키워드와 같은 헤쉬태그 이름 + 포스트 이미지 1번 + 유저의 포스트 좋아요 유무.

        List<SearchSingleDto> result = queryFactory
                .select(Projections.constructor(SearchSingleDto.class
                                , post.id.as("postId")
                                , postImg.url.as("imgUrl")
                                , cool.userId.eq(userPkId).as("isCool")
                        )
                )
                .from(post)
                .leftJoin(postImg).on(post.eq(postImg.post))
                .leftJoin(cool).on(post.eq(cool.post))
                .where(
                        searchPostImgIdxEq(),
                        searchHasTagEq(keyword)
                )
                .fetch();

        boolean hasNext = getPageHasNext(result, result.size(), pageable.getPageSize());

        //TODO 전체 검색된 게시물 검색수 필요하면 쿼리 작성

        new SearchPostListResponseDto(result, hasNext);

        return null;
    }


    @Override
    public Page<SearchSingleDto> searchHashTagNonUser(String keyword, Pageable pageable) {

        //포스트 의 입력된 키워드와 같은 헤쉬태그 이름 + 포스트 이미지 1번.

        List<SearchSingleDto> result = queryFactory
                .select(Projections.fields(SearchSingleDto.class
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

        for (SearchSingleDto dto : result) {
            System.out.println("dto = " + dto);
        }

        return null;
    }


    @Override
    public Page<SearchSingleDto> searchCornOfUser(String keyword, Pageable pageable, HttpServletRequest request) {

        //콘을 검색하는거임. usernickname or corn nickname cool 대신 팔로우 여부.;
//        queryFactory.select()

        return null;
    }

    @Override
    public Page<SearchSingleDto> searchCornOfNonUser(String keyword, Pageable pageable) {
        return null;
    }

    /**
     * @param resultList   query 검색 결과 리스트.
     * @param checkSize    : 체크를 위해 원래 크기보다 +1 한 검색 결과 크기.
     * @param originalSize : pageable로 넘어온 원래 설정한 크기.
     * @return
     */
    private boolean getPageHasNext(List<?> resultList, int checkSize, int originalSize) {
        if (checkSize > originalSize) {
            resultList.remove(originalSize);
            return true;
        }
        return false;
    }

    private OrderSpecifier<?> setSort(Pageable pageable){
        if(!pageable.getSort().isEmpty()){
            for (Sort.Order o : pageable.getSort()) {
                Order direction = o.getDirection().isAscending() ? Order.ASC : Order.DESC;

                switch (o.getProperty()) {
                    case "price":
                        return new OrderSpecifier(direction,post.price);
                    case "regdt":
                        return new OrderSpecifier(direction, post.modDate);
                    case "recom":
                        return new OrderSpecifier(direction, post.recommended);
                }

            }
        }
        return null;
    }

    private BooleanBuilder searchPostLookListEq(List<Long> looks) {
        if (looks.size() == 0)
            return null;


        BooleanBuilder booleanBuilder = new BooleanBuilder();

        looks.forEach(lookId -> {
            booleanBuilder.or(postAndLook.look.id.eq(lookId));
        });

        return booleanBuilder;
    }

    private BooleanExpression searchPostCategoryIdEq(Long categoryId) {
        return categoryId != null ? post.postCategory.id.eq(categoryId) : null;
    }

    private BooleanBuilder searchPostConditionListEq(List<String> conditions) {
        if (conditions.size() == 0)
            return null;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        conditions.forEach(condition -> {
            booleanBuilder.or(post.conditions.eq(condition));
        });

        return booleanBuilder;
    }


    public BooleanExpression searchPostTitleContain(String keyword) {
        return keyword != null ? post.title.contains(keyword) : null;
    }

    //컬러 리스트
    public BooleanBuilder searchPostColorListContains(List<String> colors) {
        if (colors.size() == 0)
            return null;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        for (String color : colors) {
            booleanBuilder.or(post.color.contains(color));
        }
        return booleanBuilder;
    }

    public BooleanExpression searchCoolUserPkIdEq(Long userPkId) {
        return userPkId != null ? cool.post.eq(post).and(cool.userId.eq(userPkId)) : null;
//        return userPkId != null ? cool.userId.eq(userPkId) : null;
    }

    public BooleanExpression searchPostImgIdxEq() {
        return postImg.idx.eq(1);
    }

    public BooleanExpression searchHasTagEq(String keyword) {
        return keyword != null ? postTag.dsc.eq(keyword) : null;
    }

    public BooleanExpression searchPostTypeListEq(PostType type) {
        return type != null ? post.postType.eq(type) : null;
    }

}
