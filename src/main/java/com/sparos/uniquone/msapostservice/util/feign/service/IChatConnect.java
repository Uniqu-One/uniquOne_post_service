package com.sparos.uniquone.msapostservice.util.feign.service;

import com.sparos.uniquone.msapostservice.util.feign.dto.ChatRoomDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "uniquone-chat-service" )
public interface IChatConnect {

    @PostMapping("/chat/offer")
    void offerChat(@RequestBody ChatRoomDto chatRoomDto, @RequestHeader("token") String token);

    @GetMapping("/chat/offer/roomId")
    String offerChat(@RequestParam("postId") Long postId, @RequestParam("userId") Long userId, @RequestParam("receiverId") Long receiverId);

}
