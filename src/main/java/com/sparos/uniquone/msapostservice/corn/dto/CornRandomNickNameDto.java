package com.sparos.uniquone.msapostservice.corn.dto;

import lombok.Data;

@Data
public class CornRandomNickNameDto {
    String nickname;

    public CornRandomNickNameDto(String nickname){
        this.nickname = nickname;
    }
}
