package com.sparos.uniquone.msapostservice.post.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostRecommendListResponseDto {

    private List<PostRecommendResponseDto> content = new ArrayList<>();
    boolean isLast;

    public PostRecommendListResponseDto(List<PostRecommendResponseDto> content, boolean hasNext) {
        this.content = content;
        this.isLast = !hasNext;
    }

}
