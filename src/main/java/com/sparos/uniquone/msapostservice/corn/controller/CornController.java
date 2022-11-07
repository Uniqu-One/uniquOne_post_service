package com.sparos.uniquone.msapostservice.corn.controller;

import com.sparos.uniquone.msapostservice.corn.dto.CornCreateDto;
import com.sparos.uniquone.msapostservice.corn.dto.CornModifyDto;
import com.sparos.uniquone.msapostservice.corn.dto.CornRandomNickNameDto;
import com.sparos.uniquone.msapostservice.corn.repository.CornRepositoryCustom;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import com.sparos.uniquone.msapostservice.corn.service.ICornService;
import com.sparos.uniquone.msapostservice.util.generate.GenerateRandomConNick;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import com.sparos.uniquone.msapostservice.util.response.ExceptionCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import com.sparos.uniquone.msapostservice.util.response.UniquOneServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/corns")
@Log4j2
public class CornController {
    private final ICornService iCornService;
    private final ICornRepository iCornRepository;

    private final CornRepositoryCustom cornRepositoryCustom;


    @PostMapping("/existstitle")
    public ResponseEntity<SuccessResponse> existsTitle(@RequestBody Map<String, String> title) {
        Map<String, Boolean> exTitle = new HashMap<>();
        exTitle.put("ExistsTitle", !iCornRepository.existsByTitle(title.get("title")));
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, exTitle));
    }

    @PostMapping("")
    public ResponseEntity<SuccessResponse> cornInput(@RequestPart CornCreateDto cornCreateDto, HttpServletRequest request, @RequestPart(value = "imgfile", required = false) MultipartFile multipartFile) throws IOException {

        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iCornService.addCorn(cornCreateDto, request, multipartFile)));

    }

    @GetMapping("")
    public ResponseEntity<SuccessResponse> myCornGetInfo(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(token)) {
            if (JwtProvider.validateToken(token)) ;
            Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
            return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iCornService.getMyCornInfo(userPkId)));
        }
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_NOT_TOKEN_CODE,"토큰이없습니다."));
    }


    @GetMapping("/{cornId}")
    public ResponseEntity<SuccessResponse> otherCornGetInfo(@PathVariable("cornId") Long cornId, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(token)) {
            if (JwtProvider.validateToken(token)) ;
            Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
            return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iCornService.userGetOtherCornInfo(cornId, userPkId)));

        }
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iCornService.getOtherCornInfo(cornId)));

    }

    @GetMapping("/cornInfomodify")
    public ResponseEntity<SuccessResponse> getCornInfoModify(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(token)) {
            if (JwtProvider.validateToken(token)) ;
            Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
            return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iCornService.getCornModifyInfo(userPkId)));
        }
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_NOT_TOKEN_CODE,"토큰이없습니다."));
    }

    @PatchMapping("")
    public ResponseEntity<SuccessResponse> PatchCornInfoModify(HttpServletRequest httpServletRequest, @RequestPart CornModifyDto cornModifyDto, @RequestPart(value = "imgfile", required = false) MultipartFile multipartFile) throws IOException {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(token)) {
            if (JwtProvider.validateToken(token)) ;
            Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
            return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iCornService.patchCornModifyInfo(cornModifyDto, multipartFile, userPkId)));
        }
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_NOT_TOKEN_CODE,"토큰이없습니다."));
    }

    @GetMapping("/randNick")
    public ResponseEntity<SuccessResponse> generateCornNickName() {
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iCornService.generatedNickName()));
    }

    @GetMapping("/isexistence")
    public ResponseEntity<SuccessResponse> cornExistence(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(token)) {
            if (JwtProvider.validateToken(token)) ;
            Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
            return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iCornService.isCornExistence(userPkId)));
        }
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_NOT_TOKEN_CODE,"토큰이없습니다."));
    }

    @GetMapping("/my/dashboard")
    public ResponseEntity<SuccessResponse> getMyCornDashboard(HttpServletRequest request){
        //토큰 검사.

        return null;
    }
}
