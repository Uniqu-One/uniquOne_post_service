package com.sparos.uniquone.msapostservice.offer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OfferDetailOutDto {

    private Long postId;
    private String postTitle;
    private String postImg;
    private Long postPrice;
    private Long waitingCnt;
    private Long acceptCount;
    private Long refuseCount;
    private List<OfferDetailIndividualOutDto> offerDetailIndividualOutDtos;

}
