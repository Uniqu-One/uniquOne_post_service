package com.sparos.uniquone.msapostservice.post.dto;

import com.sparos.uniquone.msapostservice.post.domain.PostType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostListInfoDto {
    private Long postId;
    private String postImg;
    private PostType postType;
    private LocalDateTime reg_date;
}
