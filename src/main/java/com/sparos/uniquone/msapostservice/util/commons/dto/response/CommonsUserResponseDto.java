package com.sparos.uniquone.msapostservice.util.commons.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommonsUserResponseDto {
    private Long userId;
    private Long cornId;

    private String cornImg;

    public CommonsUserResponseDto(Long userId, Long cornId) {
        this.userId = userId;
        this.cornId = cornId;
    }
    public CommonsUserResponseDto(Long userId, Long cornId, String cornImg){
        this.userId = userId;
        this.cornId = cornId;
        this.cornImg = cornImg;
    }
}
