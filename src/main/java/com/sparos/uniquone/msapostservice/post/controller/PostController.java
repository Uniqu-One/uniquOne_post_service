package com.sparos.uniquone.msapostservice.post.controller;

import com.sparos.uniquone.msapostservice.post.dto.PostChatResponseDto;
import com.sparos.uniquone.msapostservice.post.service.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/posts")
@RequiredArgsConstructor
@RestController
public class PostController {

    private final IPostService iPostService;

    // 채팅 - 포스트 정보 요청 API
    @GetMapping("/chat/postInfo/{postId}/{otherUserId}")
    @ResponseBody
    public PostChatResponseDto chatPostInfo(@PathVariable("postId") Long postId, @PathVariable("otherUserId") Long otherUserId) {
        return iPostService.chatPostInfo(postId, otherUserId);
    }



}
