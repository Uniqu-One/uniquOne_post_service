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
public class NotiResponseDto {

    private NotiType notiType;

    private String nickName;

    private Long notiId;

    private Long typeId;

    private String dsc;

    private String userCornImg;

    private String postImg;

    private Boolean isCheck;

    private LocalDateTime regDate;


    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }
}
