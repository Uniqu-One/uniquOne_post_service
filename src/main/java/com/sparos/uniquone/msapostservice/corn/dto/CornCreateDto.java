package com.sparos.uniquone.msapostservice.corn.dto;

import lombok.Getter;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Getter
public class CornCreateDto {

    @NotNull
    private String title;

    @NotNull
    private String dsc;

    private String imgUrl;

}
