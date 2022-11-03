package com.sparos.uniquone.msapostservice.util.complex.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class MainContentsDto {
    private Long postId;
    private Long userId;
    private Long cornId;
    private Boolean isCool;
    private Boolean isFollow;
    private String cornTitle;
    private String cornImgUrl;
    private LocalDateTime regDate;
    private String postImgUrl;
    private Integer uniStart;

    public void addIsCool(Boolean isCool){
        this.isCool=isCool;
    }
    public void addPostImgUrl(String postImgUrl){
        this.postImgUrl=postImgUrl;
    }
}
