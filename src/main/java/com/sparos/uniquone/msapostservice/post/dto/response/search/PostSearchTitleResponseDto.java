package com.sparos.uniquone.msapostservice.post.dto.response.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSearchTitleResponseDto {
    //postId,postImg Url, cool
    private Long postId;
    private String imgUrl;
    private boolean isCool;

    public PostSearchTitleResponseDto(Long postId, String imgUrl) {
        this.postId = postId;
        this.imgUrl = imgUrl;
    }
}
