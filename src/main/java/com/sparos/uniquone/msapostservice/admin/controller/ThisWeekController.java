package com.sparos.uniquone.msapostservice.admin.controller;

import com.sparos.uniquone.msapostservice.admin.dto.request.ThisWeekUniqueOneRequestDto;
import com.sparos.uniquone.msapostservice.admin.repository.ThisWeekUniqueOneSupport;
import com.sparos.uniquone.msapostservice.admin.service.ThisWeekUniqueOneService;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin/thisWeek")
@RestController
@RequiredArgsConstructor
public class ThisWeekController {

    private final ThisWeekUniqueOneSupport support;

    private final ThisWeekUniqueOneService thisWeekUniqueOneService;

    @PostMapping()
    public ResponseEntity<SuccessResponse> createThisWeekUniqueOne(ThisWeekUniqueOneRequestDto requestDto){
        if(requestDto.getCornId().size() == 0)
            return null;

        return ResponseEntity.ok(
                SuccessResponse.of(SuccessCode.SUCCESS_CODE, thisWeekUniqueOneService.createThisWeekUniqueOne(requestDto))
        );

    }

    @GetMapping("/list")
    public ResponseEntity<SuccessResponse> getThisWeekUniqueOne(){
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, support.getUniqueOneInfo()));
    }
}
