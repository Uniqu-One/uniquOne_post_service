package com.sparos.uniquone.msapostservice.post.repository;

import com.sparos.uniquone.msapostservice.post.domain.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPostTagRepository extends JpaRepository<PostTag, Long> {
    List<PostTag> findByPostId(Long postId);
}
