package com.sparos.uniquone.msapostservice.corn.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CornInfoDto {

    private String imgUrl;

    private String title;

    private Double reviewStar;

    private Long reviewEA;

    private Long postEA;

    private Long followerEA;

    private Long followingEA;

    private String url;

    private String dsc;
}
