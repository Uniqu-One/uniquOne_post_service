package com.sparos.uniquone.msapostservice.post.repository;

import com.sparos.uniquone.msapostservice.post.domain.PostImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPostImgRepository extends JpaRepository<PostImg, Long> {

    List<PostImg> findByPostId(Long postId);

}
