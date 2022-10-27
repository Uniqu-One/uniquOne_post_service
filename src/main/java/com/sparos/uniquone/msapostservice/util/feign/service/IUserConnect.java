package com.sparos.uniquone.msapostservice.util.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "uniquone-user-service")
public interface IUserConnect {

    @GetMapping("/get/nickName/{userId}")
    String getUserNickName(@PathVariable("userId") Long userId);

}
