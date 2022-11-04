package com.sparos.uniquone.msapostservice.comment.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparos.uniquone.msapostservice.comment.domain.Comment;
import com.sparos.uniquone.msapostservice.comment.dto.response.CommentListResponseDto;
import com.sparos.uniquone.msapostservice.corn.domain.QCorn;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparos.uniquone.msapostservice.comment.domain.QComment.comment;
import static com.sparos.uniquone.msapostservice.corn.domain.QCorn.corn;

@Repository
public class CommentRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public CommentRepositorySupport(JPAQueryFactory queryFactory) {
        super(Comment.class);
        this.queryFactory = queryFactory;
    }

    //게시글의 전체 댓글 가져오기.

    public List<Comment> findAllByPost(Post post) {
        return queryFactory.selectFrom(comment)
                .leftJoin(comment.parent)
                .fetchJoin()
                .where(comment.post.id.eq(post.getId()))
                .orderBy(comment.parent.id.asc().nullsFirst(), comment.regDate.asc())
                .fetch();
    }

    public List<CommentListResponseDto> findAllByPost2(Post post) {
        return queryFactory.select(Projections.constructor(CommentListResponseDto.class
                                , comment
                                , corn.imgUrl.as("cornImgUrl")
                        )
                )
                .from(comment)
                .leftJoin(comment.parent)
                .fetchJoin()
                .leftJoin(corn).on(corn.userId.eq(comment.userId))
                .where(comment.post.id.eq(post.getId()))
                .orderBy(comment.parent.id.asc().nullsFirst(), comment.regDate.asc())
                .fetch();
    }
}
