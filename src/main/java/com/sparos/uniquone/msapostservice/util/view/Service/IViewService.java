package com.sparos.uniquone.msapostservice.util.view.Service;

import com.sparos.uniquone.msapostservice.post.dto.PostChatResponseDto;

public interface IViewService {

    PostChatResponseDto chatPostInfo(Long postId, Long otherUserId);

    Boolean chatExistPost(Long postId, Long UserId);

}
