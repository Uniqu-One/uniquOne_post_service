package com.sparos.uniquone.msapostservice.post.repository.search;

import com.sparos.uniquone.msapostservice.post.dto.response.search.PostSearchTitleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface SearchPostRepository {

    Page<PostSearchTitleResponseDto> searchPostPageOfNonUser(String keyword, Pageable pageable);

    Page<PostSearchTitleResponseDto> searchPostPageOfUser(String keyword,Long userPkId ,Pageable pageable);

    Page<PostSearchTitleResponseDto> searchHashTagPageOfNonUser(String keyword, Pageable pageable);

    Page<PostSearchTitleResponseDto> searchHashTagPageOfUser(String keyword,Long userPkId ,Pageable pageable);

    Slice<Object[]> searchPostPageBySlice(String keyword, Pageable pageable);

    Page<Object[]> searchPostPage(String type, String keyword, Pageable pageable);
}
