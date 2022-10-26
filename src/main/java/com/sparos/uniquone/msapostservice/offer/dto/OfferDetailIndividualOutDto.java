package com.sparos.uniquone.msapostservice.offer.dto;

import com.sparos.uniquone.msapostservice.offer.domain.Offer;
import com.sparos.uniquone.msapostservice.offer.domain.OfferType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OfferDetailIndividualOutDto {

    private Long offerId;
    private String cornImg;
    private String uesrNickName;
    private String offerRegDate;
    private String offerCheckDate;
    private Long offerPrice;
    private OfferType offerType;

}
