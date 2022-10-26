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
public class OfferCheckedInPutDto {

    private Long offerId;
    private OfferType offerType;

}
