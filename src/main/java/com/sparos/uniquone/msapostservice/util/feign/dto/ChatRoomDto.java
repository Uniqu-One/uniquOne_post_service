package com.sparos.uniquone.msapostservice.util.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDto {

    private Long receiverId;

    private Long postId;

    private ChatRoomType chatType;

}
