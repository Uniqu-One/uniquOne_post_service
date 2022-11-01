package com.sparos.uniquone.msapostservice.unistar.controller;

import com.sparos.uniquone.msapostservice.unistar.dto.request.UniStarRequestDto;
import com.sparos.uniquone.msapostservice.unistar.service.IUniStarService;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
                        uniStarService.createUniStar(postId, request)));
    }

    @GetMapping("/all")
    public ResponseEntity<SuccessResponse> getPostListOfMyUniStar(@RequestParam(value = "level", required = false) Integer level, HttpServletRequest request, Pageable pageable) {
        return ResponseEntity.ok(
                SuccessResponse.of(
                        SuccessCode.SUCCESS_CODE,
                        uniStarService.getPostListOfMyUniStar(level, pageable, request)
                )
        );
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<SuccessResponse> modifyUniStar(@PathVariable Long postId, @RequestBody UniStarRequestDto requestDto, HttpServletRequest request) {
        return ResponseEntity.ok(
                SuccessResponse.of(SuccessCode.SUCCESS_CODE,
                        uniStarService.modifyUniStar(postId, requestDto, request)));
    }

    @PatchMapping("/{uniStarId}/uni")
    public ResponseEntity<SuccessResponse> updateUniStarOfUniId(@PathVariable Long uniStarId, @RequestBody UniStarRequestDto requestDto, HttpServletRequest request) {
        return ResponseEntity.ok(
                SuccessResponse.of(SuccessCode.SUCCESS_CODE,
                        uniStarService.updateUniStarOfUniId(uniStarId, requestDto, request))
        );
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<SuccessResponse> deleteUniStar(@PathVariable Long postId, HttpServletRequest request) {
        return ResponseEntity.ok(
                SuccessResponse.of(SuccessCode.SUCCESS_CODE,
                        uniStarService.deleteUniStar(postId, request)));
    }
}
