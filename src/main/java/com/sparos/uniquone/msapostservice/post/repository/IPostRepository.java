package com.sparos.uniquone.msapostservice.post.repository;

import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.post.domain.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IPostRepository extends JpaRepository<Post, Long> {

    @Query("select p.id from Post p where p.corn.id =:cornId")
    Optional<List<Long>> findIdByCornId(@Param("cornId") Long cornId);

    List<Post> findByCornId(Long cornId);

    List<Post> findByCornIdOrderByRegDateDesc(Long cornId);

    List<Post> findByCornIdAndPostTypeOrderByRegDateDesc(Long cornId, PostType postType);

    Optional<Post> findByIdAndCornId(Long postId, Long cornId);
}
