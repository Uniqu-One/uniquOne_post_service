package com.sparos.uniquone.msapostservice.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FollowingInfoDto {
    private String cornImgUrl;
    private String cornTitle;
    private String userName;
    private Long cornId;

    public void addUserName(String userName){
        this.userName=userName;
    }
}
