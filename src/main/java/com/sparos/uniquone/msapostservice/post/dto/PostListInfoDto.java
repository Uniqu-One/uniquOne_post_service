package com.sparos.uniquone.msapostservice.post.dto;

import com.sparos.uniquone.msapostservice.post.domain.PostType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostListInfoDto {
    private Long postId;
    private String postImg;
    private PostType postType;
    private LocalDateTime reg_date;
}
