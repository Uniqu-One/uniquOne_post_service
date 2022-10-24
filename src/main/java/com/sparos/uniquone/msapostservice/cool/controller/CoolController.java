package com.sparos.uniquone.msapostservice.cool.controller;

import com.sparos.uniquone.msapostservice.cool.service.ICoolService;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cool")
public class CoolController {
    private final ICoolService iCoolService;
    @PostMapping("/{postId}")
    public ResponseEntity<SuccessResponse> addCool(HttpServletRequest httpServletRequest, @PathVariable("postId")Long postId){
        Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,iCoolService.addCool(userPkId,postId)));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<SuccessResponse> delCool(HttpServletRequest httpServletRequest, @PathVariable("postId")Long postId){
        Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,iCoolService.delCool(userPkId,postId)));
    }

}
