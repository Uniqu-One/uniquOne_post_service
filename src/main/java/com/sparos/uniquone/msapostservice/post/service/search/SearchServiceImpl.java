package com.sparos.uniquone.msapostservice.post.service.search;

import com.sparos.uniquone.msapostservice.post.dto.request.search.PostSearchRequestDto;
import com.sparos.uniquone.msapostservice.post.dto.response.search.PostFullSearchResponseDto;
import com.sparos.uniquone.msapostservice.post.dto.response.search.PostSearchTitleResponseDto;
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
    public Slice<PostSearchTitleResponseDto> fullSearchPostPageOfUser(PostSearchRequestDto postSearchRequestDto, Pageable pageable, HttpServletRequest request) {

        //포스트 검색 유저의 좋아요 여부 검색 해야됨.
        searchPostOfUser(postSearchRequestDto, pageable ,request);
        //해쉬태그 검색(완전 일치) 유저의 좋아요 여부 검색 해야됨.
        searchHashTagOfUser(postSearchRequestDto.getKeyword(), pageable, request);
        //콘(유저) 검색
        searchCornOfUser(postSearchRequestDto.getKeyword(), pageable, request);

        //세개 결과값 합쳐서 반환.

        return null;
    }

    @Override
    public Slice<PostFullSearchResponseDto> fullSearchPostPageOfNonUser(PostSearchRequestDto postSearchRequestDto, Pageable pageable) {

        //포스트 검색 유저의 좋아요 여부 검색 안함.
        searchPostOfNonUser(postSearchRequestDto, pageable);
        //해쉬태그 검색(완전 일치) 유저의 좋아요 여부 검색 안함.
        searchHashTagNonUser(postSearchRequestDto.getKeyword(), pageable);
        //콘(유저) 검색 팔로우 여부 검색 안함
        searchCornOfNonUser(postSearchRequestDto.getKeyword(), pageable);
        //세개 결과값 합쳐서 반환.

        return null;
    }

    @Override
    public Page<PostSearchTitleResponseDto> searchPostOfUser(PostSearchRequestDto postSearchRequestDto, Pageable pageable, HttpServletRequest request) {
//        postRepository.searchPostOfUser();
        return null;
    }

    @Override
    public Page<PostSearchTitleResponseDto> searchPostOfNonUser(PostSearchRequestDto postSearchRequestDto, Pageable pageable) {
        return null;
    }

    @Override
    public Page<PostSearchTitleResponseDto> searchHashTagNonUser(String keyword, Pageable pageable) {
        return null;
    }

    @Override
    public Page<PostSearchTitleResponseDto> searchHashTagOfUser(String keyword, Pageable pageable, HttpServletRequest request) {
        return null;
    }

    @Override
    public Page<PostSearchTitleResponseDto> searchCornOfUser(String keyword, Pageable pageable, HttpServletRequest request) {
        return null;
    }

    @Override
    public Page<PostSearchTitleResponseDto> searchCornOfNonUser(String keyword, Pageable pageable) {
        return null;
    }
}
