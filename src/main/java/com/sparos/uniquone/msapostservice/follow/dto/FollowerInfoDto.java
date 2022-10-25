package com.sparos.uniquone.msapostservice.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowerInfoDto {
    private String cornImgUrl;
    private String cornTitle;
    private Long cornId;
    private String userName;
    private Long userId;

}
