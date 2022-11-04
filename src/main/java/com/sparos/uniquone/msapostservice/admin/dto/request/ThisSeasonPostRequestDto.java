package com.sparos.uniquone.msapostservice.admin.dto.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ThisSeasonPostRequestDto {

    private List<Long> postId = new ArrayList<>();

    private String seasonType;

}
