package com.sparos.uniquone.msapostservice.post.dto.search.response;

import lombok.Data;

@Data
public class FullSearchResponseDto {
    private SearchPostListResponseDto postList;

    private SearchHashTagListResponseDto hashTagList;

    private SearchCornListResponseDto cornList;

    public FullSearchResponseDto(SearchPostListResponseDto postList, SearchHashTagListResponseDto hashTagList, SearchCornListResponseDto cornList){
        this.postList = postList;
        this.hashTagList = hashTagList;
        this.cornList = cornList;
    }
}
