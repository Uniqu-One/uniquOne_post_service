package com.sparos.uniquone.msapostservice.trade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class TradeOutDto {

    private Long tradeId;
    private LocalDateTime regDate;
    private Boolean isReview;

    private Long postId;
    private String postTitle;
    private String postImg;
    private Long price;


}
