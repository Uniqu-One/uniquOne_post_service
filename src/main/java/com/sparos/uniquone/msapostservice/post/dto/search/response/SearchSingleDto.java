package com.sparos.uniquone.msapostservice.post.dto.search.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@Slf4j
public class SearchSingleDto {
    //postId,postImg Url, cool
    private Long postId;
    private String imgUrl;
    private boolean isCool;
    private Long coolCnt;

    public SearchSingleDto(Long postId, String imgUrl) {
        this.postId = postId;
        this.imgUrl = imgUrl;
        this.isCool = false;
    }

    public SearchSingleDto(Long postId, String imgUrl, Long coolCnt) {
        this.postId = postId;
        this.imgUrl = imgUrl;
        this.isCool = false;
        this.coolCnt = coolCnt == null ? 0 : coolCnt;
    }
    public SearchSingleDto(Long postId, String imgUrl, boolean isCool) {
        this.postId = postId;
        this.imgUrl = imgUrl;
        this.isCool = isCool;
        this.coolCnt = coolCnt == null ? 0 : coolCnt;
    }

    public SearchSingleDto(Long postId, String imgUrl, boolean isCool, Long coolCnt){
        this.postId = postId;
        this.imgUrl = imgUrl;
        this.isCool = isCool;
        this.coolCnt = coolCnt == null ? 0 : coolCnt;
    }
}
