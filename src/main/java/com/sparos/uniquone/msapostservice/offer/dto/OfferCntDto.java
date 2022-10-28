package com.sparos.uniquone.msapostservice.offer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OfferCntDto {

    private Long postId;
    private String postTitle;
    private Long price;
    private Long waitingCnt;
    private Long acceptCount;
    private Long refuseCount;

}
