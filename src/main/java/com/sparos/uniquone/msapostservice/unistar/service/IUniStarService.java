package com.sparos.uniquone.msapostservice.unistar.service;

import com.sparos.uniquone.msapostservice.unistar.dto.request.UniStarRequestDto;
import com.sparos.uniquone.msapostservice.unistar.dto.response.UniStarGetPostListResponseDto;
import com.sparos.uniquone.msapostservice.unistar.dto.response.UniStarResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface IUniStarService {
    UniStarResponseDto createUniStar(Long postId, HttpServletRequest request);

    UniStarResponseDto modifyUniStar(Long postId, UniStarRequestDto requestDto , HttpServletRequest request);

    UniStarResponseDto updateUniStarOfUniId(Long uniStarId, UniStarRequestDto requestDto, HttpServletRequest request);
    String deleteUniStar(Long postId, HttpServletRequest request);

    UniStarGetPostListResponseDto getPostListOfMyUniStar(Integer uniStarLevel, Pageable pageable, HttpServletRequest request);
}
