package com.sparos.uniquone.msapostservice.post.dto.search.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class SearchPostListResponseDto {
    private List<SearchSingleDto> searchSingleDtoList;
    private boolean hasNext;

    public SearchPostListResponseDto(){}

    public SearchPostListResponseDto(List<SearchSingleDto> searchSingleDtoList, boolean hasNext) {
        this.searchSingleDtoList = searchSingleDtoList;
        this.hasNext = hasNext;
    }
}
