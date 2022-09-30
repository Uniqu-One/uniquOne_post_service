package com.sparos.uniquone.msapostservice.post.repository;

import com.sparos.uniquone.msapostservice.post.domain.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostCategoryRepository extends JpaRepository<PostCategory, Long> {
}
