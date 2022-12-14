package com.sparos.uniquone.msapostservice.post.repository.search;

import com.sparos.uniquone.msapostservice.post.dto.search.request.SearchRequestDto;
import com.sparos.uniquone.msapostservice.post.dto.search.response.SearchCornListResponseDto;
import com.sparos.uniquone.msapostservice.post.dto.search.response.SearchHashTagListResponseDto;
import com.sparos.uniquone.msapostservice.post.dto.search.response.SearchPostListResponseDto;
import com.sparos.uniquone.msapostservice.post.dto.search.response.SearchSingleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;

public interface SearchPostRepository {
    SearchPostListResponseDto searchPostOfUser(SearchRequestDto searchRequestDto, Long userPkId, Pageable pageable);

    SearchPostListResponseDto searchPostOfNonUser(SearchRequestDto searchRequestDto, Pageable pageable);

    SearchHashTagListResponseDto searchHashTagNonUser(String keyword, Pageable pageable);

    SearchHashTagListResponseDto searchHashTagOfUser(String keyword, Long userPkId, Pageable pageable);

    SearchCornListResponseDto searchCornOfUser(String keyword, Long userPkId ,Pageable pageable);

    SearchCornListResponseDto searchCornOfNonUser(String keyword, Pageable pageable);

}
