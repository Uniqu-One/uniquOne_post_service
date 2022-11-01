package com.sparos.uniquone.msapostservice.post.dto;

import lombok.Data;

@Data
public class PostRecommendResponseDto {
    private Long postId;
    private String imgUrl;
    private boolean isCool;

    public PostRecommendResponseDto(Long postId, String imgUrl) {
        this.postId = postId;
        this.imgUrl = imgUrl;
    }
}
