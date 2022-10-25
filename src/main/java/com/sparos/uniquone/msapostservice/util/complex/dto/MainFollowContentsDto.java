package com.sparos.uniquone.msapostservice.util.complex.dto;

import lombok.*;

import java.util.List;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class MainFollowContentsDto {
    private Long postId;
    private Long userId;
    private Long cornId;
    private Boolean isCool;
    private Boolean isFollow;
    private String cornTitle;
    private String cornImgUrl;
    private List<String> postImgUrlList;

    public void addIsCool(Boolean isCool){
        this.isCool=isCool;
    }
    public void addPostImgUrlList(List<String> postImgUrlList){
        this.postImgUrlList=postImgUrlList;
    }
}