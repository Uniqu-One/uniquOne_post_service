package com.sparos.uniquone.msapostservice.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostListInfoDto {
    private Long postId;
    private String postImg;
    private String postType;
    private LocalDateTime reg_date;
}
