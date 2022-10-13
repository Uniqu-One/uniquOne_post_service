package com.sparos.uniquone.msapostservice.post.repository;

import com.sparos.uniquone.msapostservice.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByCornId(Long cornId);

    Optional<Post> findByIdAndCornId(Long postId, Long cornId);
}
