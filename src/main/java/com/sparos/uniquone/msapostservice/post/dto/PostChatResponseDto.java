package com.sparos.uniquone.msapostservice.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostChatResponseDto {

    private Long postId;
    private String postImg;
    private String cornImg;


}
