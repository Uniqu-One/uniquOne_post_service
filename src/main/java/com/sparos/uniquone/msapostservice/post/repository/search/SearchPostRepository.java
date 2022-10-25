package com.sparos.uniquone.msapostservice.post.repository.search;

import com.sparos.uniquone.msapostservice.post.dto.search.request.SearchRequestDto;
import com.sparos.uniquone.msapostservice.post.dto.search.response.SearchPostListResponseDto;
import com.sparos.uniquone.msapostservice.post.dto.search.response.SearchSingleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;

public interface SearchPostRepository {

    SearchPostListResponseDto searchPostOfUser(SearchRequestDto searchRequestDto, Long userPkId, Pageable pageable);

    Page<SearchSingleDto> searchPostOfNonUser(String keyword, Pageable pageable);

    Page<SearchSingleDto> searchHashTagNonUser(String keyword, Pageable pageable);

    SearchPostListResponseDto searchHashTagOfUser(String keyword, Long userPkId, Pageable pageable);

    Page<SearchSingleDto> searchCornOfUser(String keyword, Pageable pageable, HttpServletRequest request);

    Page<SearchSingleDto> searchCornOfNonUser(String keyword, Pageable pageable);

}
