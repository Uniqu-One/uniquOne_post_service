package com.sparos.uniquone.msapostservice.follow.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FollowerInfoDto {
    private String cornImgUrl;
    private String userName;
    private Long userId;
}
