package com.sparos.uniquone.msapostservice.comment.controller;

import com.sparos.uniquone.msapostservice.comment.dto.request.CommentCreateRequestDto;
import com.sparos.uniquone.msapostservice.comment.dto.request.CommentUpdateRequestDto;
import com.sparos.uniquone.msapostservice.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<?> createComment(@RequestBody CommentCreateRequestDto commentCreateRequestDto, HttpServletRequest request){
        return commentService.createComment(commentCreateRequestDto, request);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> findByAll(@PathVariable("postId") Long postId){
        return commentService.getAllCommentsByPost(postId);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<?> updateCommentById(@PathVariable("commentId") Long commentId ,@RequestBody CommentUpdateRequestDto commentUpdateRequestDto
    ,HttpServletRequest request){
        return commentService.updateCommentById(commentId, commentUpdateRequestDto.getContent(), request);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteCommentById(@PathVariable("commentId") Long commentId, HttpServletRequest request){
        return commentService.deleteCommentById(commentId, request);
    }

}
