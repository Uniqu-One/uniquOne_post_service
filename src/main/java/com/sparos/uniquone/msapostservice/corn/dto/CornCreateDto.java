package com.sparos.uniquone.msapostservice.corn.dto;

import lombok.Getter;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Getter
public class CornCreateDto {

    @NonNull
    private Long userId;

    @NotNull
    private String title;

    @NotNull
    private String dsc;

    private String imgUrl;

}
