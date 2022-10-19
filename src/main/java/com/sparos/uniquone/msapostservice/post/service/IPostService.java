package com.sparos.uniquone.msapostservice.post.service;

import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.post.dto.PostChatResponseDto;
import com.sparos.uniquone.msapostservice.post.dto.PostInputDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IPostService {

    Object addPost(PostInputDto postInputDto, List<MultipartFile> multipartFileList, Long userId) throws IOException;

    Object modifyPost(PostInputDto postInputDto, Long userId, Long postId);

    Object delPost(Long postId, Long userId);

    Object getOtherPostAllList(Long userId, Long cornId);

    Object getOtherPostProductList(Long userId, Long cornId, Pageable pageable);

    Object getOtherPostStyleList(Long userId, Long cornId);

    Object getModPostInfo(Long userId, Long postId);


}
