package com.sparos.uniquone.msapostservice.util.complex.Service;

import com.sparos.uniquone.msapostservice.post.dto.PostChatResponseDto;

public interface IComplexService {

    PostChatResponseDto chatPostInfo(Long postId, Long otherUserId);

    Boolean chatExistPost(Long postId, Long UserId);

}
