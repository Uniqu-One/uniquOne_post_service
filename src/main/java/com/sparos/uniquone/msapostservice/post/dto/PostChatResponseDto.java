package com.sparos.uniquone.msapostservice.post.dto;

import com.sparos.uniquone.msapostservice.post.domain.PostType;
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
    private String postDsc;
    private Long postPrice;
    private PostType postType; // enum
    private Boolean isOffer;
    private String postImg;
    private String cornImg;

}
