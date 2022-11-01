package com.sparos.uniquone.msapostservice.util.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Chat {

    enum ChatType{
        ENTER, TALK, NOTICE;
    }

    private String id;

    private Long senderId;

    private String chatRoomId;

    private String message;

    private ChatType type;

    private LocalDateTime regDate;

}
