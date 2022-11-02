package com.sparos.uniquone.msapostservice.post.dto;

import lombok.Data;

@Data
public class PostRecommendResponseDto {
    private Long postId;
    private String imgUrl;
    private Boolean isCool;

    public PostRecommendResponseDto(Long postId, String imgUrl) {
        this.postId = postId;
        this.imgUrl = imgUrl;
    }

    public PostRecommendResponseDto(Long postId, String imgUrl, Boolean isCool){
        this.postId = postId;
        this.imgUrl = imgUrl;
        this.isCool = isCool;
    }
}
