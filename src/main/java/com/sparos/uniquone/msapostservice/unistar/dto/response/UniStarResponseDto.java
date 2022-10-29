package com.sparos.uniquone.msapostservice.unistar.dto.response;

import lombok.Data;

@Data
public class UniStarResponseDto {
    private Long postId;
    private Long userId;
    private Integer grade;

    public UniStarResponseDto(Long postId, Long userId, Integer grade) {
        this.postId = postId;
        this.userId = userId;
        this.grade = grade;
    }
}
