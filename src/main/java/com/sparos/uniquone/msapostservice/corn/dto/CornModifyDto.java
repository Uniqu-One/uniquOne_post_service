package com.sparos.uniquone.msapostservice.corn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CornModifyDto {

    private String imgUrl;

    private String title;

    private String url;

    private String dsc;
}