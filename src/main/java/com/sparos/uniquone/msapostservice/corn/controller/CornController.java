package com.sparos.uniquone.msapostservice.corn.controller;

import com.sparos.uniquone.msapostservice.corn.dto.CornCreateDto;
import com.sparos.uniquone.msapostservice.corn.service.ICornService;
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
    @PostMapping("")
    public ResponseEntity<CornCreateDto> cornInput(@RequestPart CornCreateDto cornCreateDto,@RequestPart(value = "imgfile", required = false) MultipartFile multipartFile) throws IOException {
        iCornService.AddCorn(cornCreateDto, multipartFile);
        return ResponseEntity.ok(cornCreateDto);
    }


}
