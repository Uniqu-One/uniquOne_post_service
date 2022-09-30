package com.sparos.uniquone.msapostservice.post.repository;

import com.sparos.uniquone.msapostservice.post.domain.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostTagRepository extends JpaRepository<PostTag, Long> {
}
