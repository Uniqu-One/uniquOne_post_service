package com.sparos.uniquone.msapostservice.util.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OfferChatRoomCreateDto {

    private Long postId;
    private Long userId;
    private Long receiverId;
    private Long postPrice;
    private Long offerPrice;

}
