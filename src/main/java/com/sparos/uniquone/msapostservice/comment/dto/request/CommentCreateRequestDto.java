package com.sparos.uniquone.msapostservice.comment.dto.request;

import lombok.Data;

@Data
public class CommentCreateRequestDto {
    private Long postId;
    private Long parentId;
    private String content;
}
