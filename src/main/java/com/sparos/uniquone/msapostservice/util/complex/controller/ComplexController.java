package com.sparos.uniquone.msapostservice.util.complex.controller;

import com.sparos.uniquone.msapostservice.post.dto.PostChatResponseDto;
import com.sparos.uniquone.msapostservice.util.complex.Service.IComplexService;
import com.sparos.uniquone.msapostservice.util.complex.dto.ChatPushDto;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class ComplexController {

    private final IComplexService iComplexService;

    // 채팅 - 포스트 정보 요청 API
    @GetMapping("/chat/postInfo/{postId}/{otherUserId}")
    public PostChatResponseDto chatPostInfo(@PathVariable("postId") Long postId, @PathVariable("otherUserId") Long otherUserId) {
        return iComplexService.chatPostInfo(postId, otherUserId);
    }

    // 채팅 - 콘에 해당하는 포스트 존재 확인 API
    @GetMapping("/chat/existPost/{postId}/{userId}")
    public Boolean chatExistPost(@PathVariable("postId") Long postId, @PathVariable("userId") Long userId) {
        return iComplexService.chatExistPost(postId, userId);
    }

    @GetMapping("/main/search/contents/follow")
    public ResponseEntity<SuccessResponse> getMain(HttpServletRequest httpServletRequest,@PageableDefault(size = 10) Pageable pageable){
        Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,iComplexService.getMainFollowContent(userPkId,pageable)));
    }

    @GetMapping("/main/search/contents/recommend")
    public ResponseEntity<SuccessResponse> getRecommendMain(HttpServletRequest httpServletRequest,@PageableDefault(size = 10) Pageable pageable){
        Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,iComplexService.getMainRecommendContent(userPkId,pageable)));
    }

    // 채팅 - 알림 푸시
    @PostMapping("/chat/sendPush")
    public void chatPush(@RequestBody ChatPushDto chatPushDto){
        iComplexService.chatPush(chatPushDto);
    }

    @GetMapping("/chat/{postId}")
    public Long getUserIdByCorn(@PathVariable("postId") Long postId){
        return iComplexService.getUserIdByCorn(postId);
    }

    @GetMapping("/posts/detail/cornInfo/{cornId}")
    public ResponseEntity<SuccessResponse> getPostDetailPateCornInfo(@PathVariable("cornId")Long cornId){
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,iComplexService.getPostDetailPageCronInfo(cornId)));
    }
}
