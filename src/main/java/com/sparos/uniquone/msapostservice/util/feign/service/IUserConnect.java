package com.sparos.uniquone.msapostservice.util.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@FeignClient(name = "uniquone-gateway-service", url = "http://13.125.66.71:8000/feignUser")
public interface IUserConnect {

    @GetMapping("/get/nickName/{userId}")
    String getUserNickName(@PathVariable("userId") Long userId);

}
