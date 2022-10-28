package com.sparos.uniquone.msapostservice.offer.dto;

import com.sparos.uniquone.msapostservice.offer.domain.OfferType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OfferDetailIndividualOutDto {

    private Long offerId;
    private String cornImg;
    private Long userId;
    private String userNickName;
    private String offerRegDate;
    private String offerCheckDate;
    private Long offerPrice;
    private OfferType offerType;
    private String chatRoomId;

}
