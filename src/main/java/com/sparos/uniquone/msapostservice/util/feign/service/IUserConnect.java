package com.sparos.uniquone.msapostservice.util.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@FeignClient(name = "uniquone-user-service", url = "http://uniquone-gateway-service:8000")
public interface IUserConnect {

    @GetMapping("/feignUser/get/nickName/{userId}")
    String getUserNickName(@PathVariable("userId") Long userId);

}
