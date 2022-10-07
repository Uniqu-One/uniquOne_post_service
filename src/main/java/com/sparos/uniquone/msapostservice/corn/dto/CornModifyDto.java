package com.sparos.uniquone.msapostservice.corn.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CornModifyDto {

    private String imgUrl;

    private String title;

    private String url;

    private String dsc;
}