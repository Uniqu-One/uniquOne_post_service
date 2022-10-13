package com.sparos.uniquone.msapostservice.post.repository;

import com.sparos.uniquone.msapostservice.post.domain.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPostCategoryRepository extends JpaRepository<PostCategory, Long> {
    Optional<PostCategory> findByName(String name);
}
