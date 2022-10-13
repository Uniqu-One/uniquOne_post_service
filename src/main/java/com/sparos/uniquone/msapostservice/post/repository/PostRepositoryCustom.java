package com.sparos.uniquone.msapostservice.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PostRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

}
