package com.sparos.uniquone.msapostservice.comment.dto;

import lombok.NonNull;

import javax.persistence.Column;

public class CommentCreateDto {

    private Long userId;

    @NonNull
    private Long postId;

    @NonNull
    private String comment;

    private Integer ref;

    private Integer level;

    private Integer refOrder;

    private Long parentId;

}
