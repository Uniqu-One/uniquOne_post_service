package com.sparos.uniquone.msapostservice.post.dto.search.response;

import lombok.Data;

import java.util.List;

@Data
public class SearchHashTagListResponseDto {

    List<SearchSingleDto> result;
    boolean hasNext;
    private Long totalSearchCnt;

    public SearchHashTagListResponseDto(List<SearchSingleDto> result, Long totalSearchCnt, boolean hasNext) {
        this.result = result;
        this.totalSearchCnt = totalSearchCnt;
        this.hasNext = hasNext;
    }
}
