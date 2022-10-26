package com.sparos.uniquone.msapostservice.post.dto.search.response;

import lombok.Data;

import java.util.List;

@Data
public class SearchCornListResponseDto {

    private List<SearchCornDto> result;
    private boolean hasNext;
    private Long totalSearchCnt;

    public SearchCornListResponseDto(List<SearchCornDto> result,Long totalSearchCnt ,boolean hasNext){
        this.result = result;
        this.totalSearchCnt = totalSearchCnt;
        this.hasNext = hasNext;
    }
}
