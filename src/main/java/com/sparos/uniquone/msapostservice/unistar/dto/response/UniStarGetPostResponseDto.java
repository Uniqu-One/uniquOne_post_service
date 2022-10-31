package com.sparos.uniquone.msapostservice.unistar.dto.response;

import lombok.Data;

@Data
public class UniStarGetPostResponseDto {

    private Long postId;
    private Long uniStarId;
    private String postImgUrl;
    private Integer uniStarLevel;

    public UniStarGetPostResponseDto(Long postId, Long uniStarId, String postImgUrl, Integer uniStarLevel){
        this.postId = postId;
        this.uniStarId = postId;
        this.postImgUrl = postImgUrl;
        this.uniStarLevel = uniStarLevel;
    }

}
