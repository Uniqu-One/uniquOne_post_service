package com.sparos.uniquone.msapostservice.follow.dto;

import lombok.Getter;

@Getter
public class FollowingInfoDto {
    private String cornImgUrl;
    private String cornTitle;
    private String userName;
    private String cornId;

    public void addUserName(String userName){
        this.userName=userName;
    }
}
