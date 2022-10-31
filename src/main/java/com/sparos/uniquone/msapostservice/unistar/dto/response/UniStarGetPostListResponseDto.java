package com.sparos.uniquone.msapostservice.unistar.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UniStarGetPostListResponseDto {
    private List<UniStarGetPostResponseDto> result = new ArrayList<>();

    private boolean hasNext;

    public UniStarGetPostListResponseDto(List<UniStarGetPostResponseDto> result, boolean hasNext) {
        this.result = result;
        this.hasNext = hasNext;
    }

}
