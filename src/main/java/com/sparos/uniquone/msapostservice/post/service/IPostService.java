package com.sparos.uniquone.msapostservice.post.service;

import com.sparos.uniquone.msapostservice.post.dto.PostInputDto;
import com.sparos.uniquone.msapostservice.post.dto.PostRecommendListResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface IPostService {

    Object addPost(PostInputDto postInputDto, List<MultipartFile> multipartFileList, Long userId) throws IOException;

    Object modifyPost(PostInputDto postInputDto, Long userId, Long postId);

    Object delPost(Long postId, Long userId);

    Object getMyPostAllList(Long userId, Pageable pageable);

    Object getOtherPostAllList(Long cornId,Pageable pageable);

    Object getMyPostProductList(Long userId, Pageable pageable);

    Object getOtherPostProductList(Long cornId, Pageable pageable);

    Object getMyPostStyleList(Long userId, Pageable pageable);

    Object getOtherPostStyleList(Long cornId,Pageable pageable);

    Object getModPostInfo(Long userId, Long postId);

    Object getPostDetailInfo(Long postId);
    PostRecommendListResponseDto getPostCoolListOfNonUser(HttpServletRequest request, Pageable pageable);

    Object getUserPostDetailInfo(Long userId,Long postId);
}
