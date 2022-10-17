package com.sparos.uniquone.msapostservice.post.repository;

import com.sparos.uniquone.msapostservice.post.domain.PostImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPostImgRepository extends JpaRepository<PostImg, Long> {

    List<PostImg> findByPostId(Long postId);

    PostImg findOneByPostIdAndIdx(Long postId, Integer idx);

    @Query("select p.url from PostImg p where p.post.id =:postId")
    String findUrlById(@Param("postId") Long postId);
}
