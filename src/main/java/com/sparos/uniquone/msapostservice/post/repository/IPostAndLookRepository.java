package com.sparos.uniquone.msapostservice.post.repository;

import com.sparos.uniquone.msapostservice.post.domain.PostAndLook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostAndLookRepository extends JpaRepository<PostAndLook, Long> {
}
