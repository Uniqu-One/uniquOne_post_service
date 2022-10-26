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
    private String postImg;
    private Long price;
    private Integer totalCount;
    private Integer acceptCount;
    private Integer refuseCount;

}
