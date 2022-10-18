package com.sparos.uniquone.msapostservice.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreateDto {

    private Long tradeId;

    private Long postId;

    private Double star;

    private String dsc;
}
