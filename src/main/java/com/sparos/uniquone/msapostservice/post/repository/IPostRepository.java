package com.sparos.uniquone.msapostservice.post.repository;

import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.post.domain.PostType;
import com.sparos.uniquone.msapostservice.post.repository.search.SearchPostRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.List;
import java.util.Optional;

public interface IPostRepository extends JpaRepository<Post, Long>, SearchPostRepository{

    @Query("select p.id from Post p where p.corn.id =:cornId")
    List<Long> findIdByCornId(@Param("cornId") Long cornId);

    List<Post> findByCornIdAndPostType(Long cornId, PostType postType);

    List<Post> findByCornId(Long cornId);

    Slice<Post> findByCornIdOrderByRegDateDesc(Long cornId, Pageable pageable);

    Slice<Post> findByCornIdAndPostTypeOrderByRegDateDesc(Long cornId, PostType postType,Pageable pageable);

    Optional<Post> findByIdAndCornId(Long postId, Long cornId);

    Optional<Post> findByIdAndPostTypeOrIdAndPostType(Long postId, PostType postType, Long postId2, PostType postType2);
}
