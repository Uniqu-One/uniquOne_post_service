package com.sparos.uniquone.msapostservice.post.repository;

import com.sparos.uniquone.msapostservice.post.domain.PostAndLook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPostAndLookRepository extends JpaRepository<PostAndLook, Long> {
    List<PostAndLook> findByPostId(Long postId);
    List<PostAndLook> findByLookId(Long lookId);
}
