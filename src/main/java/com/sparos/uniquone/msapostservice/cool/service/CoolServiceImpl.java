package com.sparos.uniquone.msapostservice.cool.service;

import com.sparos.uniquone.msapostservice.cool.domain.Cool;
import com.sparos.uniquone.msapostservice.cool.repository.ICoolRepository;
import com.sparos.uniquone.msapostservice.post.repository.IPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoolServiceImpl implements ICoolService {
    private final IPostRepository iPostRepository;
    private final ICoolRepository iCoolRepository;

    @Override
    public Object addCool(Long userId, Long postId) {
        Cool cool = iCoolRepository.save(Cool.builder().post(iPostRepository.findById(postId).get()).userId(userId).build());
        return "좋아요가 완료되었습니다.";
    }

    @Override
    public Object delCool(Long userId, Long postId) {
        iCoolRepository.deleteByUserIdAndPostId(userId, postId);
        return "좋아요가 취소가 완료되었습니다.";
    }
}
