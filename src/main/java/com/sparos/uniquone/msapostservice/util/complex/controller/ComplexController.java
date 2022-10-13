package com.sparos.uniquone.msapostservice.util.complex.controller;

import com.sparos.uniquone.msapostservice.post.dto.PostChatResponseDto;
import com.sparos.uniquone.msapostservice.util.complex.Service.IComplexService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class ComplexController {

    private final IComplexService iViewService;

    // 채팅 - 포스트 정보 요청 API
    @GetMapping("/chat/postInfo/{postId}/{otherUserId}")
    public PostChatResponseDto chatPostInfo(@PathVariable("postId") Long postId, @PathVariable("otherUserId") Long otherUserId) {
        return iViewService.chatPostInfo(postId, otherUserId);
    }

    // 채팅 - 콘에 해당하는 포스트 존재 확인 API
    @GetMapping("/chat/existPost/{postId}/{userId}")
    public Boolean chatExistPost(@PathVariable("postId") Long postId, @PathVariable("userId") Long userId) {
        return iViewService.chatExistPost(postId, userId);
    }



}