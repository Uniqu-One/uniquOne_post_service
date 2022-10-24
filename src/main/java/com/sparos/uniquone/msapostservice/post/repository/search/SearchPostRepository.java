package com.sparos.uniquone.msapostservice.post.repository.search;

import com.sparos.uniquone.msapostservice.post.dto.request.search.PostSearchRequestDto;
import com.sparos.uniquone.msapostservice.post.dto.response.search.PostFullSearchResponseDto;
import com.sparos.uniquone.msapostservice.post.dto.response.search.PostSearchTitleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import javax.servlet.http.HttpServletRequest;

public interface SearchPostRepository {

    Page<PostSearchTitleResponseDto> searchPostOfUser(PostSearchRequestDto postSearchRequestDto, Long userPkId, Pageable pageable);

    Page<PostSearchTitleResponseDto> searchPostOfNonUser(String keyword, Pageable pageable);

    Page<PostSearchTitleResponseDto> searchHashTagNonUser(String keyword, Pageable pageable);

    Page<PostSearchTitleResponseDto> searchHashTagOfUser(String keyword, Pageable pageable, HttpServletRequest request);

    Page<PostSearchTitleResponseDto> searchCornOfUser(String keyword, Pageable pageable, HttpServletRequest request);

    Page<PostSearchTitleResponseDto> searchCornOfNonUser(String keyword, Pageable pageable);

}
