package com.sparos.uniquone.msapostservice.post.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.post.domain.PostType;
import com.sparos.uniquone.msapostservice.post.domain.QPostAndLook;
import com.sparos.uniquone.msapostservice.post.dto.search.request.SearchRequestDto;
import com.sparos.uniquone.msapostservice.post.dto.search.response.*;
import com.sparos.uniquone.msapostservice.util.response.ExceptionCode;
import com.sparos.uniquone.msapostservice.util.response.UniquOneServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.sparos.uniquone.msapostservice.cool.domain.QCool.cool;
import static com.sparos.uniquone.msapostservice.corn.domain.QCorn.corn;
import static com.sparos.uniquone.msapostservice.follow.domain.QFollow.follow;
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

    //**???????????? ?????? LEFT JOIN -> [ A left join B on (a.id = b.id) ]
    //A(??????) ???????????? B(?????????) ???????????? ????????? ?????????,
    // B???????????? A???????????? ???????????? ?????? ?????? ?????? A??? ?????? ????????? ?????? ????????? ?????????.

    @Override
    public SearchPostListResponseDto searchPostOfUser(SearchRequestDto searchRequestDto, Long userPkId, Pageable pageable) {

        if (!StringUtils.hasText(searchRequestDto.getKeyword())) {
            throw new UniquOneServiceException(ExceptionCode.KEYWORD_EMPTY, HttpStatus.OK);
        }

        List<SearchSingleDto> result;

        boolean hasNext = false;
        Long totalSearchCount = 0L;

        if (searchRequestDto.getLooks().size() != 0) {
            result = detailSearchPostCaseInLooksOfUser(searchRequestDto, userPkId, pageable);

            totalSearchCount = getCntDetailSearchPostCaseInLooksOfUser(searchRequestDto, userPkId, pageable);

        } else {
            result = queryFactory
                    .select(Projections.constructor(SearchSingleDto.class
                                    , post.id.as("postId")
                                    , postImg.url.as("imgUrl")
                                    , cool.userId.eq(userPkId).as("isCool")
                                    , post.recommended.as("coolCnt")
                            )
                    )
                    .from(post)
                    .leftJoin(postImg).on(post.eq(postImg.post))
                    .leftJoin(cool).on(post.eq(cool.post).and(cool.userId.eq(userPkId)))
                    .where(
                            searchPostImgIdxEq(),
                            //????????? ?????????
                            searchPostTitleContain(searchRequestDto.getKeyword())
                            //?????? ?????????
                            , searchPostColorListContains(searchRequestDto.getColors())
                            //???????????? ID
                            , searchPostCategoryIdEq(searchRequestDto.getCategoryId())
                            //?????? ?????????
                            , searchPostConditionListEq(searchRequestDto.getConditions())
                            //????????? ??????(?????????, ?????????)
                            , searchPostTypeListEq(searchRequestDto.getPostType())
                    )
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize() + 1)
                    //?????? ??????.
                    .orderBy(setSort(pageable))
                    .fetch();

            totalSearchCount = queryFactory
                    .select(post.count())
                    .from(post)
                    .leftJoin(postImg).on(post.eq(postImg.post))
                    .leftJoin(cool).on(post.eq(cool.post))
                    .where(
                            searchPostImgIdxEq(),
                            //????????? ?????????
                            searchPostTitleContain(searchRequestDto.getKeyword())
                            //?????? ?????????
                            , searchPostColorListContains(searchRequestDto.getColors())
                            //???????????? ID
                            , searchPostCategoryIdEq(searchRequestDto.getCategoryId())
                            //?????? ?????????
                            , searchPostConditionListEq(searchRequestDto.getConditions())
                            //????????? ??????(?????????, ?????????)
                            , searchPostTypeListEq(searchRequestDto.getPostType())
                    )
                    .fetchOne();

        }

        //?????? ????????? ?????? ?????? ????????? ??? Pageable??? size ?????? ?????? ????????? ??????!.
//        boolean hasNext = false;
//        if(result.size() > pageable.getPageSize()){
//            result.remove(pageable.getPageSize());
//            hasNext = true;
//        }
        hasNext = getPageHasNext(result, result.size(), pageable.getPageSize());
        log.info("keyword = {} ", searchRequestDto.getKeyword());


        return new SearchPostListResponseDto(result, totalSearchCount, hasNext);
    }

    @Override
    public SearchPostListResponseDto searchPostOfNonUser(SearchRequestDto searchRequestDto, Pageable pageable) {

        if (!StringUtils.hasText(searchRequestDto.getKeyword())) {
            throw new UniquOneServiceException(ExceptionCode.KEYWORD_EMPTY, HttpStatus.OK);
        }

        List<SearchSingleDto> result;

        boolean hasNext = false;
        Long totalSearchCount = 0L;

        if (searchRequestDto.getLooks().size() != 0) {
            result = detailSearchPostCaseInLooksOfNonUser(searchRequestDto, pageable);

            totalSearchCount = getCntDetailSearchPostCaseInLooksOfNonUser(searchRequestDto, pageable);

        } else {
            result = queryFactory
                    .select(Projections.constructor(SearchSingleDto.class
                                    , post.id.as("postId")
                                    , postImg.url.as("imgUrl")
                                    , post.recommended.as("coolCnt")
                            )
                    )
                    .from(post)
                    .leftJoin(postImg).on(post.eq(postImg.post))
                    .where(
                            searchPostImgIdxEq(),
                            //????????? ?????????
                            searchPostTitleContain(searchRequestDto.getKeyword())
                            //?????? ?????????
                            , searchPostColorListContains(searchRequestDto.getColors())
                            //???????????? ID
                            , searchPostCategoryIdEq(searchRequestDto.getCategoryId())
                            //?????? ?????????
                            , searchPostConditionListEq(searchRequestDto.getConditions())
                            //????????? ??????(?????????, ?????????)
                            , searchPostTypeListEq(searchRequestDto.getPostType())
                    )
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize() + 1)
                    //?????? ??????.
                    .orderBy(setSort(pageable))
                    .fetch();

            totalSearchCount = queryFactory
                    .select(post.count())
                    .from(post)
                    .leftJoin(postImg).on(post.eq(postImg.post))
                    .where(
                            searchPostImgIdxEq(),
                            //????????? ?????????
                            searchPostTitleContain(searchRequestDto.getKeyword())
                            //?????? ?????????
                            , searchPostColorListContains(searchRequestDto.getColors())
                            //???????????? ID
                            , searchPostCategoryIdEq(searchRequestDto.getCategoryId())
                            //?????? ?????????
                            , searchPostConditionListEq(searchRequestDto.getConditions())
                            //????????? ??????(?????????, ?????????)
                            , searchPostTypeListEq(searchRequestDto.getPostType())
                    )
                    .fetchOne();

        }

//        log.info("result size = {} ", result.size());
        log.info("keyword = {} ", searchRequestDto.getKeyword());

        hasNext = getPageHasNext(result, result.size(), pageable.getPageSize());

        return new SearchPostListResponseDto(result, totalSearchCount, hasNext);
    }

    @Override
    public SearchHashTagListResponseDto searchHashTagOfUser(String keyword, Long userPkId, Pageable pageable) {
        //????????? ??? ????????? ???????????? ?????? ???????????? ?????? + ????????? ????????? 1??? + ????????? ????????? ????????? ??????.
        List<SearchSingleDto> result = queryFactory
                .select(Projections.constructor(SearchSingleDto.class
                                , post.id.as("postId")
                                , postImg.url.as("imgUrl")
                                , cool.userId.eq(userPkId).as("isCool")
                        )
                )
                .from(post)
                .leftJoin(postImg).on(post.eq(postImg.post))
                .leftJoin(postTag).on(post.eq(postTag.post))
                .leftJoin(cool).on(post.eq(cool.post).and(cool.userId.eq(userPkId)))
                .where(
                        searchPostImgIdxEq(),
                        searchHasTagEq(keyword)
                )
                //????????? ??????.
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                //?????? ??????.
                .orderBy(setSort(pageable))
                .fetch();

        //hasNext
        boolean hasNext = getPageHasNext(result, result.size(), pageable.getPageSize());

        //?????? ?????????. TODO ????????? ?????? ?????? ?????? ?????? ?????????.
        Long totalSearchCnt = queryFactory.select(post.count())
                .from(post)
                .leftJoin(postImg).on(post.eq(postImg.post))
                .leftJoin(postTag).on(post.eq(postTag.post))
                .leftJoin(cool).on(post.eq(cool.post).and(cool.userId.eq(userPkId)))
                .where(
                        searchPostImgIdxEq(),
                        searchHasTagEq(keyword)
                ).fetchOne();

        //?????? ??????.
        return new SearchHashTagListResponseDto(result, totalSearchCnt, hasNext);
    }


    @Override
    public SearchHashTagListResponseDto searchHashTagNonUser(String keyword, Pageable pageable) {

        //????????? ??? ????????? ???????????? ?????? ???????????? ?????? + ????????? ????????? 1???.
        List<SearchSingleDto> result = queryFactory
                .select(Projections.constructor(SearchSingleDto.class
                                , post.id.as("postId")
                                , postImg.url.as("imgUrl")
                        )
                )
                .from(post)
                .leftJoin(postImg).on(post.eq(postImg.post))
                .leftJoin(postTag).on(post.eq(postTag.post))
                .where(
                        searchPostImgIdxEq(),
                        searchHasTagEq(keyword)
                )
                //????????? ??????.
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                //?????? ??????.
                .orderBy(setSort(pageable))
                .fetch();

        //hasNext
        boolean hasNext = getPageHasNext(result, result.size(), pageable.getPageSize());

        //?????? ?????????. TODO ????????? ?????? ?????? ?????? ?????? ?????????.
        Long totalSearchCnt = queryFactory.select(post.count())
                .from(post)
                .leftJoin(postImg).on(post.eq(postImg.post))
                .leftJoin(postTag).on(post.eq(postTag.post))
                .where(
                        searchPostImgIdxEq(),
                        searchHasTagEq(keyword)
                ).fetchOne();

        //?????? ??????.
        return new SearchHashTagListResponseDto(result, totalSearchCnt, hasNext);
    }

    @Override
    public SearchCornListResponseDto searchCornOfUser(String keyword, Long userPkId, Pageable pageable) {
        //?????? ??????????????????. usernickname or corn nickname cool ?????? ????????? ??????.;
        List<SearchCornDto> result = queryFactory
                .select(Projections.constructor(SearchCornDto.class
                        , corn.id.as("cornId")
                        , corn.imgUrl.as("cornImgUrl")
                        , corn.title.as("cornNickName")
                        , corn.userNickName.as("userNickName")
                        , follow.userId.eq(userPkId).as("isFollow")
                ))
                .from(corn)
                .leftJoin(follow).on(corn.id.eq(follow.corn.id).and(follow.userId.eq(userPkId)))
                .where(searchCornNickOrUserNickContain(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                //?????? ??????.
                .orderBy(corn.title.asc())
                .orderBy(corn.userNickName.asc())
                .fetch();

        //hasNext
        boolean hasNext = getPageHasNext(result, result.size(), pageable.getPageSize());
        //?????? ??? ?????????.
        Long totalSearchCnt = queryFactory
                .select(corn.count())
                .from(corn)
                .leftJoin(follow).on(corn.id.eq(follow.corn.id).and(follow.userId.eq(userPkId)))
                .where(searchCornNickOrUserNickContain(keyword))
                .fetchOne();
        //?????? ?????? ????????? Dto ??????.
        return new SearchCornListResponseDto(result, totalSearchCnt, hasNext);
    }

    @Override
    public SearchCornListResponseDto searchCornOfNonUser(String keyword, Pageable pageable) {
        List<SearchCornDto> result = queryFactory
                .select(Projections.constructor(SearchCornDto.class
                        , corn.id.as("cornId")
                        , corn.imgUrl.as("cornImgUrl")
                        , corn.title.as("cornNickName")
                        , corn.userNickName.as("userNickName")
                ))
                .from(corn)
                .where(searchCornNickOrUserNickContain(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                //?????? ??????.
                .fetch();

        //hasNext
        boolean hasNext = getPageHasNext(result, result.size(), pageable.getPageSize());
        //?????? ??? ?????????.
        Long totalSearchCnt = queryFactory
                .select(corn.count())
                .from(corn)
                .where(searchCornNickOrUserNickContain(keyword))
                .fetchOne();
        //?????? ?????? ????????? Dto ??????.
        return new SearchCornListResponseDto(result, totalSearchCnt, hasNext);
    }

    private List<SearchSingleDto> detailSearchPostCaseInLooksOfUser(SearchRequestDto searchRequestDto, Long userPkId, Pageable pageable) {
        return queryFactory
                .select(Projections.constructor(SearchSingleDto.class
                                , post.id.as("postId")
                                , postImg.url.as("imgUrl")
                                , cool.userId.eq(userPkId).as("isCool")
                                , post.recommended.as("coolCnt")
                        )
                )
                .from(post)
                .leftJoin(postImg).on(post.eq(postImg.post))
                .leftJoin(cool).on(post.eq(cool.post).and(cool.userId.eq(userPkId)))
//                .leftJoin(cool).on(post.eq(cool.post))
                .leftJoin(postAndLook).on(post.eq(postAndLook.post))
                .where(
                        searchPostImgIdxEq(),
                        //????????? ?????????
                        searchPostTitleContain(searchRequestDto.getKeyword())
                        //?????? ?????????
                        , searchPostColorListContains(searchRequestDto.getColors())
                        //???????????? ID
                        , searchPostCategoryIdEq(searchRequestDto.getCategoryId())
                        //?????? ?????????
                        , searchPostConditionListEq(searchRequestDto.getConditions())
                        //????????? ??????(?????????, ?????????)
                        , searchPostTypeListEq(searchRequestDto.getPostType())
                        //????????? ID
                        , searchPostLookListEq(searchRequestDto.getLooks())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                //?????? ??????.
                .orderBy(setSort(pageable))
                .fetch();
    }

    private List<SearchSingleDto> detailSearchPostCaseInLooksOfNonUser(SearchRequestDto searchRequestDto, Pageable pageable) {
        return queryFactory
                .select(Projections.constructor(SearchSingleDto.class
                                , post.id.as("postId")
                                , postImg.url.as("imgUrl")
                                , post.recommended.as("coolCnt")
                        )
                )
                .from(post)
                .leftJoin(postImg).on(post.eq(postImg.post))
                .leftJoin(postAndLook).on(post.eq(postAndLook.post))
                .where(
                        searchPostImgIdxEq(),
                        //????????? ?????????
                        searchPostTitleContain(searchRequestDto.getKeyword())
                        //?????? ?????????
                        , searchPostColorListContains(searchRequestDto.getColors())
                        //???????????? ID
                        , searchPostCategoryIdEq(searchRequestDto.getCategoryId())
                        //?????? ?????????
                        , searchPostConditionListEq(searchRequestDto.getConditions())
                        //????????? ??????(?????????, ?????????)
                        , searchPostTypeListEq(searchRequestDto.getPostType())
                        //????????? ID
                        , searchPostLookListEq(searchRequestDto.getLooks())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                //?????? ??????.

                .fetch();
    }


    private Long getCntDetailSearchPostCaseInLooksOfUser(SearchRequestDto searchRequestDto, Long userPkId, Pageable pageable) {
        return queryFactory
                .select(post.count())
                .from(post)
                .leftJoin(postImg).on(post.eq(postImg.post))
                .leftJoin(cool).on(post.eq(cool.post).and(cool.userId.eq(userPkId)))
                .leftJoin(postAndLook).on(post.eq(postAndLook.post))
                .where(
                        searchPostImgIdxEq(),
                        //????????? ?????????
                        searchPostTitleContain(searchRequestDto.getKeyword())
                        //?????? ?????????
                        , searchPostColorListContains(searchRequestDto.getColors())
                        //???????????? ID
                        , searchPostCategoryIdEq(searchRequestDto.getCategoryId())
                        //?????? ?????????
                        , searchPostConditionListEq(searchRequestDto.getConditions())
                        //????????? ??????(?????????, ?????????)
                        , searchPostTypeListEq(searchRequestDto.getPostType())
                        //????????? ID
                        , searchPostLookListEq(searchRequestDto.getLooks())
                )
                .fetchOne();
    }

    private Long getCntDetailSearchPostCaseInLooksOfNonUser(SearchRequestDto searchRequestDto, Pageable pageable) {
        return queryFactory
                .select(post.count())
                .from(post)
                .leftJoin(postImg).on(post.eq(postImg.post))
                .leftJoin(postAndLook).on(post.eq(postAndLook.post))
                .where(
                        searchPostImgIdxEq(),
                        //????????? ?????????
                        searchPostTitleContain(searchRequestDto.getKeyword())
                        //?????? ?????????
                        , searchPostColorListContains(searchRequestDto.getColors())
                        //???????????? ID
                        , searchPostCategoryIdEq(searchRequestDto.getCategoryId())
                        //?????? ?????????
                        , searchPostConditionListEq(searchRequestDto.getConditions())
                        //????????? ??????(?????????, ?????????)
                        , searchPostTypeListEq(searchRequestDto.getPostType())
                        //????????? ID
                        , searchPostLookListEq(searchRequestDto.getLooks())
                )
                .fetchOne();
    }

    /**
     * @param resultList   query ?????? ?????? ?????????.
     * @param checkSize    : ????????? ?????? ?????? ???????????? +1 ??? ?????? ?????? ??????.
     * @param originalSize : pageable??? ????????? ?????? ????????? ??????.
     * @return
     */
    private boolean getPageHasNext(List<?> resultList, int checkSize, int originalSize) {
        if (checkSize > originalSize) {
            resultList.remove(originalSize);
            return true;
        }
        return false;
    }

    private OrderSpecifier<?> setSort(Pageable pageable) {
        if (!pageable.getSort().isEmpty()) {
            for (Sort.Order o : pageable.getSort()) {
                Order direction = o.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (o.getProperty()) {
                    case "price":
                        return new OrderSpecifier(direction, post.price);
                    case "regdt":
                        return new OrderSpecifier(direction, post.modDate);
                    case "recom":
                        return new OrderSpecifier(direction, post.recommended);
                }

            }
        }
        return new OrderSpecifier<>(Order.DESC, post.regDate);
    }

    //    private BooleanBuilder searchPostLookListEq(List<Long> looks) {
    private BooleanExpression searchPostLookListEq(List<Long> looks) {
        if (looks.size() == 0)
            return null;

        QPostAndLook subPostAndLook = new QPostAndLook("subPostAndLook");


        BooleanBuilder booleanBuilder = new BooleanBuilder();

//        looks.forEach(lookId -> {
//            booleanBuilder.or(postAndLook.look.id.eq(lookId));
//        });
//        return booleanBuilder;

        looks.forEach(lookId -> {
            booleanBuilder.or(subPostAndLook.look.id.eq(lookId));
        });

        return postAndLook.look.id.eq(
                    JPAExpressions.select(subPostAndLook.look.id.max())
                        .from(subPostAndLook)
                        .where(
                                booleanBuilder
                        )
        );

    }

    private BooleanExpression searchCornNickNameContain(String keyword) {
        return StringUtils.hasText(keyword) ? corn.title.contains(keyword) : null;
    }

    private BooleanExpression searchCornUserNickNameContain(String keyword) {
        return StringUtils.hasText(keyword) ? corn.userNickName.contains(keyword) : null;
    }

    private BooleanBuilder searchCornNickOrUserNickContain(String keyword) {
        if (!StringUtils.hasText(keyword))
            return null;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.or(corn.title.contains(keyword)).or(corn.userNickName.contains(keyword));

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

    //?????? ?????????
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
