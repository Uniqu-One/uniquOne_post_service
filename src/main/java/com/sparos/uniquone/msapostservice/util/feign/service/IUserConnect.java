package com.sparos.uniquone.msapostservice.util.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@FeignClient(name = "uniquone-gateway-service", url = "http://gateway-server")
public interface IUserConnect {

    @GetMapping("/feignUser/get/nickName/{userId}")
    String getUserNickName(@PathVariable("userId") Long userId);

}
