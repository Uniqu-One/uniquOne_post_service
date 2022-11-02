package com.sparos.uniquone.msapostservice.util.complex.Service;

import com.sparos.uniquone.msapostservice.post.dto.PostChatResponseDto;
import com.sparos.uniquone.msapostservice.util.complex.dto.ChatPushDto;
import org.springframework.data.domain.Pageable;

public interface IComplexService {

    PostChatResponseDto chatPostInfo(Long postId, Long otherUserId);

    Boolean chatExistPost(Long postId, Long UserId);

    Object getMainFollowContent(Long userId, Pageable pageable);

    void chatPush(ChatPushDto chatPushDto);

    Long getUserIdByCorn(Long postId);

}
