package com.sparos.uniquone.msapostservice.corn.controller;

import com.sparos.uniquone.msapostservice.corn.dto.CornCreateDto;
import com.sparos.uniquone.msapostservice.corn.service.ICornService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/corns")
public class CornController {
    private final ICornService iCornService;
    @PostMapping("")
    public ResponseEntity<CornCreateDto> cornInput(@RequestBody CornCreateDto cornCreateDto){
        iCornService.AddCorn(cornCreateDto);
        return ResponseEntity.ok(cornCreateDto);
    }


}
