package com.sparos.uniquone.msapostservice.cool.controller;

import com.sparos.uniquone.msapostservice.cool.service.ICoolService;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cool")
public class CoolController {
    private final ICoolService iCoolService;
    @PostMapping("/{postId}/{userId}")
    public ResponseEntity<SuccessResponse> addCool(@PathVariable("postId")Long postId,@PathVariable("userId") Long userId){
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,iCoolService.addCool(userId,postId)));
    }

    @DeleteMapping("/{postId}/{userId}")
    public ResponseEntity<SuccessResponse> delCool(@PathVariable("postId")Long postId,@PathVariable("userId") Long userId){
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,iCoolService.delCool(userId,postId)));
    }

}
