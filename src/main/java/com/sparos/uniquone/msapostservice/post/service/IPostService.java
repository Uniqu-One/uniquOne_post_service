package com.sparos.uniquone.msapostservice.post.service;

import com.sparos.uniquone.msapostservice.post.dto.PostChatResponseDto;
import org.springframework.web.bind.annotation.PathVariable;

public interface IPostService {

    PostChatResponseDto chatPostInfo(Long postId, Long otherUserId);

}
