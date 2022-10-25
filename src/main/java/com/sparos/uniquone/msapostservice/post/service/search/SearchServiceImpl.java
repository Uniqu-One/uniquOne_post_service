package com.sparos.uniquone.msapostservice.post.service.search;

import com.sparos.uniquone.msapostservice.post.dto.search.request.SearchRequestDto;
import com.sparos.uniquone.msapostservice.post.dto.search.response.FullSearchResponseDto;
import com.sparos.uniquone.msapostservice.post.dto.search.response.SearchSingleDto;
import com.sparos.uniquone.msapostservice.post.repository.IPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService{

    private final IPostRepository postRepository;


    @Override
    public Slice<SearchSingleDto> fullSearchPostPageOfUser(SearchRequestDto searchRequestDto, Pageable pageable, HttpServletRequest request) {

        //포스트 검색 유저의 좋아요 여부 검색 해야됨.
        searchPostOfUser(searchRequestDto, pageable ,request);
        //해쉬태그 검색(완전 일치) 유저의 좋아요 여부 검색 해야됨.
        searchHashTagOfUser(searchRequestDto.getKeyword(), pageable, request);
        //콘(유저) 검색
        searchCornOfUser(searchRequestDto.getKeyword(), pageable, request);

        //세개 결과값 합쳐서 반환.

        return null;
    }

    @Override
    public Slice<FullSearchResponseDto> fullSearchPostPageOfNonUser(SearchRequestDto searchRequestDto, Pageable pageable) {

        //포스트 검색 유저의 좋아요 여부 검색 안함.
        searchPostOfNonUser(searchRequestDto, pageable);
        //해쉬태그 검색(완전 일치) 유저의 좋아요 여부 검색 안함.
        searchHashTagNonUser(searchRequestDto.getKeyword(), pageable);
        //콘(유저) 검색 팔로우 여부 검색 안함
        searchCornOfNonUser(searchRequestDto.getKeyword(), pageable);
        //세개 결과값 합쳐서 반환.

        return null;
    }

    @Override
    public Page<SearchSingleDto> searchPostOfUser(SearchRequestDto searchRequestDto, Pageable pageable, HttpServletRequest request) {
//        postRepository.searchPostOfUser();
        return null;
    }

    @Override
    public Page<SearchSingleDto> searchPostOfNonUser(SearchRequestDto searchRequestDto, Pageable pageable) {
        return null;
    }

    @Override
    public Page<SearchSingleDto> searchHashTagNonUser(String keyword, Pageable pageable) {
        return null;
    }

    @Override
    public Page<SearchSingleDto> searchHashTagOfUser(String keyword, Pageable pageable, HttpServletRequest request) {
        return null;
    }

    @Override
    public Page<SearchSingleDto> searchCornOfUser(String keyword, Pageable pageable, HttpServletRequest request) {
        return null;
    }

    @Override
    public Page<SearchSingleDto> searchCornOfNonUser(String keyword, Pageable pageable) {
        return null;
    }
}
