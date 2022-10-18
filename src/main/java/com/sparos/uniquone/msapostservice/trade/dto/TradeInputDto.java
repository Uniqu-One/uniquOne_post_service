package com.sparos.uniquone.msapostservice.trade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TradeInputDto {

    private Long buyerId;
    private Long postId;

}
