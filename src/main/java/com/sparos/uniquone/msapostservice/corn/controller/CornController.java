package com.sparos.uniquone.msapostservice.corn.controller;

import com.sparos.uniquone.msapostservice.corn.dto.CornCreateDto;
import com.sparos.uniquone.msapostservice.corn.repository.CornRepositoryCustom;
import com.sparos.uniquone.msapostservice.corn.service.ICornService;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RequiredArgsConstructor
@RestController
@RequestMapping("/corns")
public class CornController {
    private final ICornService iCornService;

    private final CornRepositoryCustom cornRepositoryCustom;
    @PostMapping("")
    public ResponseEntity<SuccessResponse> cornInput(@RequestPart CornCreateDto cornCreateDto, @RequestPart(value = "imgfile", required = false) MultipartFile multipartFile) throws IOException {

        iCornService.AddCorn(cornCreateDto, multipartFile);

        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE));

    }

    @GetMapping("/{userId}")
    public ResponseEntity<SuccessResponse> myCornGetInfo(@PathVariable("userId")Long userId){
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,iCornService.GetCornMyInfo(userId)));
    }


}
