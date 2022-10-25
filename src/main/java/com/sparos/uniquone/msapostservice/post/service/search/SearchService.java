package com.sparos.uniquone.msapostservice.post.service.search;

import com.sparos.uniquone.msapostservice.post.dto.search.request.SearchRequestDto;
import com.sparos.uniquone.msapostservice.post.dto.search.response.FullSearchResponseDto;
import com.sparos.uniquone.msapostservice.post.dto.search.response.SearchSingleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import javax.servlet.http.HttpServletRequest;

public interface SearchService {
    Slice<SearchSingleDto> fullSearchPostPageOfUser(SearchRequestDto searchRequestDto, Pageable pageable, HttpServletRequest request);

    Slice<FullSearchResponseDto> fullSearchPostPageOfNonUser(SearchRequestDto searchRequestDto, Pageable pageable);

    Page<SearchSingleDto> searchPostOfUser(SearchRequestDto searchRequestDto, Pageable pageable, HttpServletRequest request);

    Page<SearchSingleDto> searchPostOfNonUser(SearchRequestDto searchRequestDto, Pageable pageable);

    Page<SearchSingleDto> searchHashTagNonUser(String keyword, Pageable pageable);

    Page<SearchSingleDto> searchHashTagOfUser(String keyword, Pageable pageable, HttpServletRequest request);

    Page<SearchSingleDto> searchCornOfUser(String keyword, Pageable pageable, HttpServletRequest request);

    Page<SearchSingleDto> searchCornOfNonUser(String keyword, Pageable pageable);
}
