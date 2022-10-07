package com.sparos.uniquone.msapostservice.corn.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ReviewStarPostEAInfoOutputDto {

    private Double reviewStar;

    private Long reviewEA;

    private Long postEA;


}
