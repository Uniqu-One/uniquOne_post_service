package com.sparos.uniquone.msapostservice.post.service;

import com.sparos.uniquone.msapostservice.post.dto.PostChatResponseDto;
import com.sparos.uniquone.msapostservice.post.dto.PostInputDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IPostService {

    PostChatResponseDto chatPostInfo(Long postId, Long otherUserId);

    Object addPost(PostInputDto postInputDto, List<MultipartFile> multipartFileList, Long userId) throws IOException;

}
