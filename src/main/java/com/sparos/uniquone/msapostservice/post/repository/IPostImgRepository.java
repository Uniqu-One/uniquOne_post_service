package com.sparos.uniquone.msapostservice.post.repository;

import com.sparos.uniquone.msapostservice.post.domain.PostImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostImgRepository extends JpaRepository<PostImg, Long> {

    PostImg findByPostId(Long postId);

}
