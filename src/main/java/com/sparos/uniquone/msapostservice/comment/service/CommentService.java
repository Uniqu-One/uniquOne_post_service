package com.sparos.uniquone.msapostservice.comment.service;

import com.sparos.uniquone.msapostservice.comment.dto.request.CommentCreateRequestDto;
import com.sparos.uniquone.msapostservice.comment.dto.response.CommentResponseDto;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface CommentService {
    ResponseEntity<?> createComment(CommentCreateRequestDto requestDto, HttpServletRequest request);

    ResponseEntity<?> getAllCommentsByPost(Long postId);
    public ResponseEntity<?> getAllCommentsByPost2(Long postId);

    ResponseEntity<?> updateCommentById(Long commentId, String content, HttpServletRequest request);

    ResponseEntity<?> deleteCommentById(Long commentId, HttpServletRequest request);

    ResponseEntity getUserIdInfo (HttpServletRequest request);

}
