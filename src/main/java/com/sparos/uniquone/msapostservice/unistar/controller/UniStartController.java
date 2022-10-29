package com.sparos.uniquone.msapostservice.unistar.controller;

import com.sparos.uniquone.msapostservice.unistar.dto.request.UniStarRequestDto;
import com.sparos.uniquone.msapostservice.unistar.service.IUniStarService;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/unistar")
public class UniStartController {

    private final IUniStarService uniStarService;

    @PostMapping("/{postId}")
    public ResponseEntity<SuccessResponse> createUniStar(@PathVariable Long postId, HttpServletRequest request) {
        return ResponseEntity.ok(
                SuccessResponse.of(SuccessCode.SUCCESS_CODE,
                        uniStarService.createUniStar(postId,request)));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<SuccessResponse> modifyUniStar(@PathVariable Long postId, UniStarRequestDto requestDto, HttpServletRequest request) {
        return ResponseEntity.ok(
                SuccessResponse.of(SuccessCode.SUCCESS_CODE,
                        uniStarService.modifyUniStar(postId,requestDto,request)));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<SuccessResponse> deleteUniStar(@PathVariable Long postId, HttpServletRequest request) {
        return ResponseEntity.ok(
                SuccessResponse.of(SuccessCode.SUCCESS_CODE,
                        uniStarService.deleteUniStar(postId,request)));
    }
}
