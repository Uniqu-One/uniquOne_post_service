package com.sparos.uniquone.msapostservice.post.dto;

import lombok.Data;

@Data
public class PostImageListResponseDto {
    private String imgUrl;

    public PostImageListResponseDto(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
