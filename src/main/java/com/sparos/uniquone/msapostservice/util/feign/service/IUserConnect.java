package com.sparos.uniquone.msapostservice.util.feign.service;

import com.sparos.uniquone.msapostservice.util.feign.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@FeignClient(name = "uniquone-user-service")
public interface IUserConnect {

    @GetMapping("/get/nickName/{userId}")
    UserResponseDto getUserNickName(@PathVariable("userId") Long userId);

}
