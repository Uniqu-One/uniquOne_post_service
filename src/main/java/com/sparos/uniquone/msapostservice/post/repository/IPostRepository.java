package com.sparos.uniquone.msapostservice.post.repository;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostRepository extends JpaRepository<Post, Long> {

    Post findByCornId(Long cornId);
}
