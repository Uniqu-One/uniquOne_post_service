package com.sparos.uniquone.msapostservice.post.service;

import com.sparos.uniquone.msapostservice.post.dto.PostChatResponseDto;
import com.sparos.uniquone.msapostservice.post.dto.PostInputDto;
import org.springframework.web.bind.annotation.PathVariable;

public interface IPostService {

    PostChatResponseDto chatPostInfo(Long postId, Long otherUserId);

    void AddPost(PostInputDto postInputDto, Long userId);

}
