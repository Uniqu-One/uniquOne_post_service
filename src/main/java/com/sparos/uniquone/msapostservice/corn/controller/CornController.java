package com.sparos.uniquone.msapostservice.corn.controller;

import com.sparos.uniquone.msapostservice.corn.dto.CornCreateDto;
import com.sparos.uniquone.msapostservice.corn.dto.CornModifyDto;
import com.sparos.uniquone.msapostservice.corn.repository.CornRepositoryCustom;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import com.sparos.uniquone.msapostservice.corn.service.ICornService;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<SuccessResponse> cornInput(@RequestPart CornCreateDto cornCreateDto, @RequestPart(value = "imgfile", required = false) MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iCornService.AddCorn(cornCreateDto, multipartFile)));

    }

    @GetMapping("")
    public ResponseEntity<SuccessResponse> myCornGetInfo( HttpServletRequest httpServletRequest) {
        Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iCornService.GetCornInfo(userPkId)));
    }

    @GetMapping("/cornInfomodify")
    public ResponseEntity<SuccessResponse> getCornInfoModify(HttpServletRequest httpServletRequest) {
        Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iCornService.GetCornModifyInfo(userPkId)));
    }

    @PatchMapping("")
    public ResponseEntity<SuccessResponse> PatchCornInfoModify(HttpServletRequest httpServletRequest,@RequestPart CornModifyDto cornModifyDto, @RequestPart(value = "imgfile", required = false) MultipartFile multipartFile) throws IOException {
        Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iCornService.PatchCornModifyInfo(cornModifyDto, multipartFile, userPkId)));
    }

}
