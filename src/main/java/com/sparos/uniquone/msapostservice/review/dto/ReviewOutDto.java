package com.sparos.uniquone.msapostservice.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewOutDto {

    private Long reviewId;

    private Long userId;
    private String userNickName;
    private String cornImg;

    private Long postId;
    private String postImg;

    private Double star;
    private String dsc;
    private String reviewRegDate;
}
