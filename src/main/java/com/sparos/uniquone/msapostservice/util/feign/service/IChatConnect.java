package com.sparos.uniquone.msapostservice.util.feign.service;

import com.sparos.uniquone.msapostservice.util.feign.dto.ChatRoomDto;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

@FeignClient(name = "uniquone-chat-service" )
public interface IChatConnect {

    @PostMapping("/offer/chat")
    void offerChat(@RequestBody ChatRoomDto chatRoomDto, HttpServletRequest request);

}
