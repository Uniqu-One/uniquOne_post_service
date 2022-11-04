package com.sparos.uniquone.msapostservice.admin.dto.response;

import lombok.Data;

@Data
public class ThisSeasonPostResponseDto {

    private Long postId;
    private String postImgUrl;

    public ThisSeasonPostResponseDto(Long postId, String postImgUrl){
        this.postId = postId;
        this.postImgUrl = postImgUrl;
    }

}
