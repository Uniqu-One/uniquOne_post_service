package com.sparos.uniquone.msapostservice.util.complex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CornInfoDto {
    private Long userId;
    private Long cornId;
    private String userNickName;
    private String cornImgUrl;
    private String cornTitle;
    private String cornDsc;
    private Long reviewEA;
    private Long postEA;

    public void addUserNickName(String userNickName){
        this.userNickName=userNickName;

    }
}
