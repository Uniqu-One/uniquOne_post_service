package com.sparos.uniquone.msapostservice.post.dto.response.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@Slf4j
public class PostSearchTitleResponseDto {
    //postId,postImg Url, cool
    private Long postId;
    private String imgUrl;
    private boolean isCool;

    public PostSearchTitleResponseDto(Long postId, String imgUrl) {
        this.postId = postId;
        this.imgUrl = imgUrl;
    }

    public PostSearchTitleResponseDto(Long postId, String imgUrl, boolean isCool){
        this.postId = postId;
        this.imgUrl = imgUrl;
        this.isCool = isCool;
    }
}
