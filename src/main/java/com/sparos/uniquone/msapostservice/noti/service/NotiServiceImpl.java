package com.sparos.uniquone.msapostservice.noti.service;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import com.sparos.uniquone.msapostservice.noti.domain.Noti;
import com.sparos.uniquone.msapostservice.noti.dto.NotiOutDto;
import com.sparos.uniquone.msapostservice.noti.repository.INotiRepository;
import com.sparos.uniquone.msapostservice.post.repository.IPostImgRepository;
import com.sparos.uniquone.msapostservice.util.feign.service.IUserConnect;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import com.sparos.uniquone.msapostservice.util.response.ExceptionCode;
import com.sparos.uniquone.msapostservice.util.response.UniquOneServiceException;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotiServiceImpl implements INotiService {

    private final INotiRepository iNotiRepository;
    private final ICornRepository iCornRepository;
    private final IPostImgRepository iPostImgRepository;
    private final IUserConnect iUserConnect;

    // 알림 조회
    @Override
    public JSONObject findMyNoti(int pageNum, HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();

        Pageable pageable = PageRequest.of(pageNum - 1, 30, Sort.by(Sort.Direction.DESC, "regDate"));
        List<Noti> notis = iNotiRepository.findByUserId(JwtProvider.getUserPkId(request), pageable);

        if (notis.isEmpty())
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED);

        jsonObject.put("data", notis.stream().map(noti ->
                entityToNotiOutDto(noti))
        );

        return jsonObject;
    }

    // 알림 확인
    @Override
    public JSONObject notiChecked(Long notiId, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        Long userId = JwtProvider.getUserPkId(request);
        Noti noti = iNotiRepository.findByIdAndUserId(notiId, userId)
                .orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED));

        noti.modCheck(true);

        noti = iNotiRepository.save(noti);

        jsonObject.put("data", noti);
        return jsonObject;
    }

    private NotiOutDto entityToNotiOutDto(Noti notification) {

        Long typeId = 0l;

        switch (notification.getNotiType()) {
            case COOL:
                typeId = notification.getCool().getPost().getId();
                break;
            case COMMENT:
                typeId = notification.getComment().getPost().getId();
                break;
            case FOLLOW:
                Corn corn = iCornRepository.findByUserId(notification.getUserId()).get();
                typeId = corn.getId();
                break;
            case OFFER:
            case OFFER_ACCEPT:
            case OFFER_REFUSE:
                typeId = notification.getOffer().getId();
                break;
            case QNA:
                typeId = notification.getQna().getId();
                break;
        }

        return NotiOutDto.builder()
                .notiType(notification.getNotiType())
                .notiId(notification.getId())
                .typeId(typeId)
                .nickName(notification.getNickName())
                .userCornImg(notification.getUserCornImg())
                .dsc(notification.getDsc())
                .isCheck(notification.getIsCheck())
                .regDate(notification.getRegDate())
                .postImg(notification.getPostImg())
                .build();
    }

    // 확인 안한 알림 갯수
    @Override
    public JSONObject notiNonCheckedCnt(HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();
        Map<String, Long> countMap = new HashMap<>();

        countMap.put("count", iNotiRepository.countByUserId(JwtProvider.getUserPkId(request)));

        jsonObject.put("data", countMap);

        return jsonObject;
    }
}