package com.sparos.uniquone.msapostservice.comment.repository;

import com.sparos.uniquone.msapostservice.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICommentRepository extends JpaRepository<Comment, Long> {
}
