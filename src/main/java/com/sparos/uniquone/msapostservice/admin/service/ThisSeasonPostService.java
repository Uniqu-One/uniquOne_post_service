package com.sparos.uniquone.msapostservice.admin.service;

import com.sparos.uniquone.msapostservice.admin.dto.request.ThisSeasonPostRequestDto;
import com.sparos.uniquone.msapostservice.admin.dto.response.ThisSeasonPostResponseDto;
import com.sparos.uniquone.msapostservice.admin.type.SeasonType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ThisSeasonPostService {
    String createThisSeasonPost(ThisSeasonPostRequestDto requestDto);

    List<ThisSeasonPostResponseDto> getSeasonPostList(String seasonType, Pageable pageable);
}
