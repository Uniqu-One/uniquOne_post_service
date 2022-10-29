package com.sparos.uniquone.msapostservice.post.repository;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparos.uniquone.msapostservice.post.domain.PostType;
import com.sparos.uniquone.msapostservice.post.dto.PostDetailInfoDto;
import com.sparos.uniquone.msapostservice.post.dto.PostListInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparos.uniquone.msapostservice.post.domain.QPost.post;
import static com.sparos.uniquone.msapostservice.post.domain.QPostAndLook.postAndLook;
import static com.sparos.uniquone.msapostservice.post.domain.QPostImg.postImg;

@RequiredArgsConstructor
@Repository
@Log4j2
public class PostRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public List<PostListInfoDto> PostProductListInfo(List<PostType> postTypeList, Long cornId, Pageable pageable) {
        List<PostListInfoDto> postListInfoDtoList = (List<PostListInfoDto>) jpaQueryFactory
                .select(Projections.constructor(PostListInfoDto.class,
                        post.id,
                        postImg.url,
                        post.postType,
                        post.regDate))
                .from(postImg)
                .join(postImg.post, post)
                .where(post.corn.id.eq(cornId), post.postType.in(postTypeList), postImg.idx.eq(1))
                .orderBy(orderByFieldList(Lists.transform(postTypeList, Functions.toStringFunction())), post.regDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .fetch();
        return postListInfoDtoList;
    }

    private OrderSpecifier<?> orderByFieldList(List<String> postTypeList) {
        return Expressions.stringTemplate("FIELD({0},{1})", post.postType, postTypeList).asc();
    }

    public PostDetailInfoDto findByPostIdPostDetailInfoDto(Long postId) {
        PostDetailInfoDto postDetailInfoDto = (PostDetailInfoDto) jpaQueryFactory
                .select(Projections.fields(
                        PostDetailInfoDto.class,
                        post.title,
                        post.dsc,
                        post.postCategory.id.as("postCategoryId"),
                        post.postType,
                        postAndLook.look.id.as("LookId"),
                        post.color,
                        post.productSize,
                        post.conditions))
                .from(post)
                .join(postAndLook.post, post)
                .where(post.id.eq(postId)).fetch();
        return postDetailInfoDto;
    }

}
