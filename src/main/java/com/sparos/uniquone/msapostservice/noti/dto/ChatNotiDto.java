package com.sparos.uniquone.msapostservice.noti.dto;

import com.sparos.uniquone.msapostservice.noti.domain.NotiType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatNotiDto {

    private NotiType notiType;

    private String nickName;

    private Long notiId;

    private String chatRoomId;

    private String dsc;

    private String userCornImg;

    private String postImg;

}
