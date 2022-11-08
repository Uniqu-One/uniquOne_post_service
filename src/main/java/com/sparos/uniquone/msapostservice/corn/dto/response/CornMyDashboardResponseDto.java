package com.sparos.uniquone.msapostservice.corn.dto.response;

import lombok.Data;

@Data
public class CornMyDashboardResponseDto {
    Long countOfFollow;
    Long countOfCool;
    Long countOfUniStar;
    Long countOfOffer;

    public CornMyDashboardResponseDto(Long countOfFollow, Long countOfCool, Long countOfUniStar, Long countOfOffer) {
        this.countOfFollow = countOfFollow;
        this.countOfCool = countOfCool;
        this.countOfUniStar = countOfUniStar;
        this.countOfOffer = countOfOffer;
    }
}
