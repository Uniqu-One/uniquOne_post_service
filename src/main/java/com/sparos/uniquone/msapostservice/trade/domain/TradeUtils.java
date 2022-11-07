package com.sparos.uniquone.msapostservice.trade.domain;

import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.trade.domain.Trade;
import com.sparos.uniquone.msapostservice.trade.dto.TradeBuyOutDto;
import com.sparos.uniquone.msapostservice.trade.dto.TradeOutDto;

public class TradeUtils {

    public static TradeOutDto entityToTradeOutDto(Trade trade, Post post, String postImg) {

        return TradeOutDto.builder()
                .tradeId(trade.getId())
                .regDate(trade.getRegDate())
                .isReview(trade.getIsReview())
                .postId(post.getId())
                .postTitle(post.getTitle())
                .price(post.getPrice())
                .postImg(postImg)
                .build();
    }

    public static TradeBuyOutDto entityToTradeBuyOutDto(Trade trade, String cornUserNickName, String cornImg, Post post, String postImg) {

        return TradeBuyOutDto.builder()
                .tradeId(trade.getId())
                .regDate(trade.getRegDate())
                .isReview(trade.getIsReview())
                .postId(post.getId())
                .postTitle(post.getTitle())
                .price(post.getPrice())
                .postImg(postImg)
                .cornName(post.getCorn().getTitle())
                .cornUserNickName(cornUserNickName)
                .cornImg(cornImg)
                .build();
    }

}
