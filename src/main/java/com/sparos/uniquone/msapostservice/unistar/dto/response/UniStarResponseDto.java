package com.sparos.uniquone.msapostservice.unistar.dto.response;

import lombok.Data;

@Data
public class UniStarResponseDto {
    private Long postId;
    private Long userId;
    private Integer level;

    public UniStarResponseDto(Long postId, Long userId, Integer level) {
        this.postId = postId;
        this.userId = userId;
        this.level = level;
    }
}
