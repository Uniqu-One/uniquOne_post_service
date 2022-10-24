package com.sparos.uniquone.msapostservice.cool.controller;

import com.sparos.uniquone.msapostservice.cool.service.ICoolService;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cool")
@Log4j2
@CrossOrigin(origins = "*")
public class CoolController {
    private final ICoolService iCoolService;
    @PostMapping("")
    public ResponseEntity<SuccessResponse> addCool(HttpServletRequest httpServletRequest, @RequestBody Map<String,Long> postId){
        Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,iCoolService.addCool(userPkId,postId.get("postId"))));
    }

    @DeleteMapping("")
    public ResponseEntity<SuccessResponse> delCool(HttpServletRequest httpServletRequest, @RequestBody Map<String,Long> postId){
        Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,iCoolService.delCool(userPkId,postId.get("postId"))));
    }

}
