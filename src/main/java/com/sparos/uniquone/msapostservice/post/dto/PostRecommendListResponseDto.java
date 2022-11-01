package com.sparos.uniquone.msapostservice.post.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostRecommendListResponseDto {

    private List<PostRecommendResponseDto> result = new ArrayList<>();
    boolean hasNext;

    public PostRecommendListResponseDto(List<PostRecommendResponseDto> result, boolean hasNext) {
        this.result = result;
        this.hasNext = hasNext;
    }

}
