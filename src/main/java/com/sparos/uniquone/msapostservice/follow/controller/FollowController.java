package com.sparos.uniquone.msapostservice.follow.controller;

import com.sparos.uniquone.msapostservice.follow.service.IFollowService;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class FollowController {
    private final IFollowService iFollowService;

    @PostMapping("/following")
    public ResponseEntity<SuccessResponse> addFollowing (HttpServletRequest httpServletRequest, @RequestBody Map<String, Long> cornId){
        Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,iFollowService.postFollowing(cornId.get("cornId"),userPkId)));
    }

    @DeleteMapping("/follow")
    public ResponseEntity<SuccessResponse> deleteFollowing (HttpServletRequest httpServletRequest, @RequestBody Map<String, Long> cornId){
        Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,iFollowService.deleteUnFollowing(cornId.get("cornId"),userPkId)));
    }

    @GetMapping("/myfollowing")
    public ResponseEntity<SuccessResponse> getFollowing (HttpServletRequest httpServletRequest){
        Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,iFollowService.getFollowing(userPkId)));
    }
    @GetMapping("/otherfollowing/{cornId}")
    public ResponseEntity<SuccessResponse> getFollowing (HttpServletRequest httpServletRequest, @PathVariable("cornId")Long cornId){
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,iFollowService.getOtherFollowing(cornId)));
    }

    @GetMapping("/follower/{cornId}")
    public ResponseEntity<SuccessResponse> getFollower (HttpServletRequest httpServletRequest,@PathVariable("cornId")Long cornId ){
        Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,iFollowService.getFollower(cornId)));
    }


}
