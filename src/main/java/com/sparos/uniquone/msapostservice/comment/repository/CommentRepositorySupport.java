package com.sparos.uniquone.msapostservice.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparos.uniquone.msapostservice.comment.domain.Comment;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CommentRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public CommentRepositorySupport(JPAQueryFactory queryFactory) {
        super(Comment.class);
        this.queryFactory = queryFactory;
    }

    //게시글의 전체 댓글 가져오기.

    public List<Comment> findAllByPost(Post post){
//        return queryFactory.selectFrom(comment)

        return null;
    }
}
