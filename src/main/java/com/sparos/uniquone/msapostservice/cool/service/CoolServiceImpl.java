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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CoolServiceImpl implements ICoolService {
    private final IPostRepository iPostRepository;
    private final ICoolRepository iCoolRepository;
    private final INotiRepository iNotiRepository;
    private final IEmitterService iEmitterService;

    @Override
    public Object addCool(Long userId, Long postId) {
        Boolean isCool = iCoolRepository.existsByUserIdAndPostId(userId,postId);
        if(!isCool) {
            Cool cool = iCoolRepository.save(Cool.builder().post(iPostRepository.findById(postId).get()).userId(userId).build());
            // 알림
            iEmitterService.send(cool.getPost().getCorn().getUserId(), cool, NotiType.COOL);
            return "좋아요가 완료되었습니다.";
        }else {
            return "이미 좋아요가 반영되었습니다.";
        }
    }

    @Override
    @Transactional(rollbackFor = UniquOneServiceException.class)
    public Object delCool(Long userId, Long postId) {
        try {
            Cool cool = iCoolRepository.findByUserIdAndPostId(userId, postId)
                    .orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED));

            iNotiRepository.updateCoolByCoolId(cool.getId());
            iCoolRepository.deleteByUserIdAndPostId(userId, postId);

            return "좋아요가 취소가 완료되었습니다.";
        }catch(Exception e){
            throw new UniquOneServiceException(ExceptionCode.NOTDELET_COOL,HttpStatus.ACCEPTED);
        }
    }
}
