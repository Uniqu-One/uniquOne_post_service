package com.sparos.uniquone.msapostservice.post.controller.search;

import com.sparos.uniquone.msapostservice.post.dto.search.request.SearchRequestDto;
import com.sparos.uniquone.msapostservice.post.dto.search.response.FullSearchResponseDto;
import com.sparos.uniquone.msapostservice.post.dto.search.response.SearchCornListResponseDto;
import com.sparos.uniquone.msapostservice.post.dto.search.response.SearchHashTagListResponseDto;
import com.sparos.uniquone.msapostservice.post.dto.search.response.SearchPostListResponseDto;
import com.sparos.uniquone.msapostservice.post.service.search.SearchService;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import com.sparos.uniquone.msapostservice.util.response.ExceptionCode;
import com.sparos.uniquone.msapostservice.util.response.UniquOneServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
@Slf4j
public class SearchController {

    private final SearchService searchService;

    //                    case "price":
    //                    case "regdt":
    //                    case "recom":
    @GetMapping("/all")
    public FullSearchResponseDto getSearchAllResult(SearchRequestDto searchRequestDto, Pageable pageable, HttpServletRequest request) {
        //헤더에 토큰 유무 판단.
        if (searchService.isUser(request)) {
            return searchService.fullSearchPostPageOfUser(searchRequestDto, pageable, request);
        }
        return searchService.fullSearchPostPageOfNonUser(searchRequestDto, pageable);
    }

    @GetMapping("/post")
    public SearchPostListResponseDto getSearchPost(SearchRequestDto searchRequestDto, Pageable pageable, HttpServletRequest request) {
        if (!StringUtils.hasText(searchRequestDto.getKeyword())) {
            throw new UniquOneServiceException(ExceptionCode.KEYWORD_EMPTY, HttpStatus.OK);
        }

        if (searchService.isUser(request)) {
            return searchService.searchPostOfUser(searchRequestDto, pageable, request);
        }
        return searchService.searchPostOfNonUser(searchRequestDto, pageable);
    }

    @GetMapping("/hashTag")
    public SearchHashTagListResponseDto getSearchHashTag(SearchRequestDto searchRequestDto, Pageable pageable, HttpServletRequest request) {
        if (!StringUtils.hasText(searchRequestDto.getKeyword())) {
            throw new UniquOneServiceException(ExceptionCode.KEYWORD_EMPTY, HttpStatus.OK);
        }

        //keyword 만 받아 버릴까?
        if (searchService.isUser(request)) {
            return searchService.searchHashTagOfUser(searchRequestDto.getKeyword(), pageable, request);
        }
        return searchService.searchHashTagNonUser(searchRequestDto.getKeyword(), pageable);
    }

    @GetMapping("/corn")
    public SearchCornListResponseDto getSearchCorn(SearchRequestDto searchRequestDto, Pageable pageable, HttpServletRequest request) {
        //keyword 만 받아 버릴까?
        if (!StringUtils.hasText(searchRequestDto.getKeyword())) {
            throw new UniquOneServiceException(ExceptionCode.KEYWORD_EMPTY, HttpStatus.OK);
        }

        if (searchService.isUser(request)) {
            return searchService.searchCornOfUser(searchRequestDto.getKeyword(), pageable, request);
        }
        return searchService.searchCornOfNonUser(searchRequestDto.getKeyword(), pageable);
    }

    @GetMapping("/user")
    public ResponseEntity<?> searchPost(SearchRequestDto searchRequestDto, Pageable pageable, HttpServletRequest request) {
//
//        Long userPkId = JwtProvider.getUserPkId(request);
//
//        postRepository.searchPostOfUser(searchRequestDto, userPkId, pageable);
        return null;
//        return ResponseEntity.status(HttpStatus.OK).body(postRepository.fullSearchPostPageOfUser(keyword,4L ,PageRequest.of(0,10)));
    }
}
