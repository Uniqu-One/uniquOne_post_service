package com.sparos.uniquone.msapostservice.trade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TradeOutDto {

    private Long tradeId;
    private LocalDateTime regDate;
    private Boolean isReview;

    private Long postId;
    private String postTitle;
    private String postImg;
    private Long price;


}
