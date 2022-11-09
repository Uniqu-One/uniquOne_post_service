package com.sparos.uniquone.msapostservice.cool.service;

import com.sparos.uniquone.msapostservice.cool.domain.Cool;
import com.sparos.uniquone.msapostservice.cool.repository.ICoolRepository;
import com.sparos.uniquone.msapostservice.noti.domain.NotiType;
import com.sparos.uniquone.msapostservice.noti.repository.INotiRepository;
import com.sparos.uniquone.msapostservice.noti.service.IEmitterService;
import com.sparos.uniquone.msapostservice.noti.service.INotiService;
import com.sparos.uniquone.msapostservice.post.repository.IPostRepository;
import com.sparos.uniquone.msapostservice.util.response.ExceptionCode;
import com.sparos.uniquone.msapostservice.util.response.UniquOneServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoolServiceImpl implements ICoolService {
    private final IPostRepository iPostRepository;
    private final ICoolRepository iCoolRepository;
    private final INotiRepository iNotiRepository;
    private final IEmitterService iEmitterService;

    @Override
    public Object addCool(Long userId, Long postId) {
        Cool cool = iCoolRepository.save(Cool.builder().post(iPostRepository.findById(postId).get()).userId(userId).build());

        // 알림
        iEmitterService.send(cool.getPost().getCorn().getUserId(), cool, NotiType.COOL);

        return "좋아요가 완료되었습니다.";
    }

    @Override
    public Object delCool(Long userId, Long postId) {
        Cool cool = iCoolRepository.findByUserIdAndPostId(userId, postId)
                .orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED));

        iCoolRepository.deleteByUserIdAndPostId(userId, postId);
        iNotiRepository.updateCoolByCoolId(cool.getId());

        return "좋아요가 취소가 완료되었습니다.";
    }
}
