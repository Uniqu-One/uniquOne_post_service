package com.sparos.uniquone.msapostservice.post.service.search;

import com.sparos.uniquone.msapostservice.post.dto.search.request.SearchRequestDto;
import com.sparos.uniquone.msapostservice.post.dto.search.response.*;
import com.sparos.uniquone.msapostservice.post.repository.IPostRepository;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import com.sparos.uniquone.msapostservice.util.response.ExceptionCode;
import com.sparos.uniquone.msapostservice.util.response.UniquOneServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final IPostRepository postRepository;

    @Override
    public boolean isUser(HttpServletRequest request) {
        if(StringUtils.hasText(request.getHeader(HttpHeaders.AUTHORIZATION))){
            if(JwtProvider.isJwtValid(request))
                return true;

            throw new UniquOneServiceException(ExceptionCode.INVALID_TOKEN, HttpStatus.OK);
        }
        return false;
    }

    @Override
    public FullSearchResponseDto fullSearchPostPageOfUser(SearchRequestDto searchRequestDto, Pageable pageable, HttpServletRequest request) {
        //포스트 검색 유저의 좋아요 여부 검색 해야됨. ->  해쉬태그 검색(완전 일치) 유저의 좋아요 여부 검색 해야됨. -> 콘(유저) 검색 -> 세개 결과값 합쳐서 반환.
        return new FullSearchResponseDto(searchPostOfUser(searchRequestDto, pageable, request)
                , searchHashTagOfUser(searchRequestDto.getKeyword(), pageable, request)
                , searchCornOfUser(searchRequestDto.getKeyword(), pageable, request));
    }

    @Override
    public FullSearchResponseDto fullSearchPostPageOfNonUser(SearchRequestDto searchRequestDto, Pageable pageable) {
        return new FullSearchResponseDto(searchPostOfNonUser(searchRequestDto, pageable)
                , searchHashTagNonUser(searchRequestDto.getKeyword(), pageable)
                , searchCornOfNonUser(searchRequestDto.getKeyword(), pageable));
    }

    @Override
    public SearchPostListResponseDto searchPostOfUser(SearchRequestDto searchRequestDto, Pageable pageable, HttpServletRequest request) {
        return postRepository.searchPostOfUser(searchRequestDto, JwtProvider.getUserPkId(request), pageable);
    }

    @Override
    public SearchPostListResponseDto searchPostOfNonUser(SearchRequestDto searchRequestDto, Pageable pageable) {
        return postRepository.searchPostOfNonUser(searchRequestDto, pageable);
    }

    @Override
    public SearchHashTagListResponseDto searchHashTagOfUser(String keyword, Pageable pageable, HttpServletRequest request) {
        return postRepository.searchHashTagOfUser(keyword, JwtProvider.getUserPkId(request), pageable);
    }

    @Override
    public SearchHashTagListResponseDto searchHashTagNonUser(String keyword, Pageable pageable) {
        return postRepository.searchHashTagNonUser(keyword, pageable);
    }

    @Override
    public SearchCornListResponseDto searchCornOfUser(String keyword, Pageable pageable, HttpServletRequest request) {
        return postRepository.searchCornOfUser(keyword, JwtProvider.getUserPkId(request), pageable);
    }

    @Override
    public SearchCornListResponseDto searchCornOfNonUser(String keyword, Pageable pageable) {
        return postRepository.searchCornOfNonUser(keyword, pageable);
    }

}
