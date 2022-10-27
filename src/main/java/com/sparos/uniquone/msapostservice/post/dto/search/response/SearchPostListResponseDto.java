package com.sparos.uniquone.msapostservice.post.dto.search.response;

import lombok.Data;

import java.util.List;

@Data
public class SearchPostListResponseDto {
    private List<SearchSingleDto> result;
    private boolean hasNext;

    private Long totalSearchCnt;
    public SearchPostListResponseDto(){}

    public SearchPostListResponseDto(List<SearchSingleDto> result, Long totalSearchCnt , boolean hasNext) {
        this.result = result;
        this.totalSearchCnt = totalSearchCnt;
        this.hasNext = hasNext;
    }
}
