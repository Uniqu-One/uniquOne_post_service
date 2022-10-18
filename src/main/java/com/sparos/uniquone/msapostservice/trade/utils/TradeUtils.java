package com.sparos.uniquone.msapostservice.trade.utils;

import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.trade.domain.Trade;

public class TradeUtils {

    public static TradeOutDto entityToTradeOutDto(Trade trade, Post post, String postImg) {

        return TradeOutDto.builder()
                .tradeId(trade.getId())
                .regDate(trade.getRegDate())
                .isReview(trade.getIsReview())
                .postId(post.getId())
                .postTitle(post.getDsc()) // todo title로 바꾸기
                .price(post.getPrice())
                .postImg(postImg)
                .build();
    }

}
