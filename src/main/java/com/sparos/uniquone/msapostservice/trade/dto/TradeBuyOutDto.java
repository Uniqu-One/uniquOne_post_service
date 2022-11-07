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
public class TradeBuyOutDto {

    private Long tradeId;
    private LocalDateTime regDate;
    private Boolean isReview;

    private Long postId;
    private String postTitle;
    private String postImg;
    private Integer price;

    private String cornName;
    private String cornUserNickName;
    private String cornImg;
}
