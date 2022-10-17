package com.sparos.uniquone.msapostservice.comment.dto.response;

import lombok.Data;

@Data
public class CommentCreateResponseDto {
    private Long postId;
    private Long parentId;
    private Long userId;
    private String content;
}
