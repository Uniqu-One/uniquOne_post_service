package com.sparos.uniquone.msapostservice.post.service.search;

import com.sparos.uniquone.msapostservice.post.dto.search.request.SearchRequestDto;
import com.sparos.uniquone.msapostservice.post.dto.search.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import javax.servlet.http.HttpServletRequest;

public interface SearchService {
    boolean isUser(HttpServletRequest request);

    FullSearchResponseDto fullSearchPostPageOfUser(SearchRequestDto searchRequestDto, Pageable pageable, HttpServletRequest request);

    FullSearchResponseDto fullSearchPostPageOfNonUser(SearchRequestDto searchRequestDto, Pageable pageable);

    SearchPostListResponseDto searchPostOfUser(SearchRequestDto searchRequestDto, Pageable pageable, HttpServletRequest request);

    SearchPostListResponseDto searchPostOfNonUser(SearchRequestDto searchRequestDto, Pageable pageable);

    SearchHashTagListResponseDto searchHashTagOfUser(String keyword, Pageable pageable, HttpServletRequest request);

    SearchHashTagListResponseDto searchHashTagNonUser(String keyword, Pageable pageable);

    SearchCornListResponseDto searchCornOfUser(String keyword, Pageable pageable, HttpServletRequest request);

    SearchCornListResponseDto searchCornOfNonUser(String keyword, Pageable pageable);


//    SearchPostListResponseDto searchPostOfUser(SearchRequestDto searchRequestDto, Long userPkId, Pageable pageable);
//
//    SearchPostListResponseDto searchPostOfNonUser(SearchRequestDto searchRequestDto, Long userPkId, Pageable pageable);
//
//    SearchHashTagListResponseDto searchHashTagNonUser(String keyword, Pageable pageable);
//
//    SearchHashTagListResponseDto searchHashTagOfUser(String keyword, Long userPkId, Pageable pageable);
//
//    SearchCornListResponseDto searchCornOfUser(String keyword, Long userPkId , Pageable pageable);
//
//    SearchCornListResponseDto searchCornOfNonUser(String keyword, Pageable pageable);


}
