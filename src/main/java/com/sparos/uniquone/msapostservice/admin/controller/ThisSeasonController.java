package com.sparos.uniquone.msapostservice.admin.controller;

import com.sparos.uniquone.msapostservice.admin.dto.request.ThisSeasonPostRequestDto;
import com.sparos.uniquone.msapostservice.admin.service.ThisSeasonPostService;
import com.sparos.uniquone.msapostservice.admin.type.SeasonType;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/thisSeason")
@RequiredArgsConstructor
public class ThisSeasonController {

    private final ThisSeasonPostService thisSeasonPostService;

    @PostMapping
    public ResponseEntity<SuccessResponse> createSeasonPostList(ThisSeasonPostRequestDto thisSeasonPostRequestDto){
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, thisSeasonPostService.createThisSeasonPost(thisSeasonPostRequestDto)));
    }


    @GetMapping("/{season}")
    public ResponseEntity<SuccessResponse> getSeasonPostList(@PathVariable String season, Pageable pageable){
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,thisSeasonPostService.getSeasonPostList(season, pageable)));
    }
}
