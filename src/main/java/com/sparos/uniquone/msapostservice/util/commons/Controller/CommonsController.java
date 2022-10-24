package com.sparos.uniquone.msapostservice.util.commons.Controller;

import com.sparos.uniquone.msapostservice.util.commons.dto.response.CommonsUserResponseDto;
import com.sparos.uniquone.msapostservice.util.commons.service.CommonsService;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/commons")
@RequiredArgsConstructor
public class CommonsController {
    private final CommonsService commonsService;

    @GetMapping("/info")
    public ResponseEntity<SuccessResponse> getCommonUserInfoWithToken(HttpServletRequest request){
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,commonsService.getCommonsUserInfoByToken(request)));
    }
}
