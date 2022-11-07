package com.sparos.uniquone.msapostservice.offer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OfferOutDto {

    private Long postId;
    private String postTitle;
    private String postImg;
    private Integer price;
    private Long waitingCnt;
    private Long acceptCount;
    private Long refuseCount;

}
