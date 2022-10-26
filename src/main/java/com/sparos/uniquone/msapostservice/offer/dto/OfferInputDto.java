package com.sparos.uniquone.msapostservice.offer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OfferInputDto {

    private Long postId;
    private Long price;

}
