package com.sparos.uniquone.msapostservice.util.complex.dto;

import com.sparos.uniquone.msapostservice.noti.domain.NotiType;
import com.sparos.uniquone.msapostservice.util.feign.dto.Chat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatPushDto {

    private Long receiverId;
    private Long postId;
    private Chat chat;

}
