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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotiServiceImpl implements INotiService {

    private final INotiRepository iNotiRepository;
    private final ICornRepository iCornRepository;
    private final IPostImgRepository iPostImgRepository;
    private final IUserConnect iUserConnect;

    // 알림 조회
    @Override
    public JSONObject findMyNoti(HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();
        List<Noti> notis = iNotiRepository.findByUserId(JwtProvider.getUserPkId(request));

        if (notis.isEmpty())
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED);

        jsonObject.put("data", notis.stream().map(noti ->
                entityToNotiOutDto(noti))
        );

        return jsonObject;
    }


    private NotiOutDto entityToNotiOutDto(Noti notification) {

        Long typeId = 0l;
        String nickName = null;
        String userCornImg = null;
        String postImg = null;
        switch (notification.getNotiType()){
            case COOL:
                typeId = notification.getCool().getPost().getId();
                nickName = iUserConnect.getUserNickName(notification.getCool().getUserId());
                userCornImg = iCornRepository.findImgUrlByUserId(notification.getCool().getUserId());
                postImg = iPostImgRepository.findUrlByPostId(notification.getCool().getPost().getId());
                break;
            case COMMENT:
                typeId = notification.getComment().getPost().getId();
                nickName = notification.getComment().getUserNickName();
                userCornImg = iCornRepository.findImgUrlByUserId(notification.getComment().getUserId());
                postImg = iPostImgRepository.findUrlByPostId(notification.getComment().getPost().getId());
                break;
            case FOLLOW:
                Corn corn = iCornRepository.findByUserId(notification.getUserId()).get();
                typeId = corn.getId();
                nickName = iUserConnect.getUserNickName(notification.getFollow().getUserId());
                userCornImg = iCornRepository.findImgUrlByUserId(notification.getFollow().getUserId());
                break;
            case QNA:
                typeId = notification.getQna().getId();
                nickName = "";
                break;
        }

        return NotiOutDto.builder()
                .notiType(notification.getNotiType())
                .notiId(notification.getId())
                .typeId(typeId)
                .nickName(nickName)
                .dsc(notification.getDsc())
                .isCheck(notification.getIsCheck())
                .regDate(notification.getRegDate())
                .postImg(postImg)
                .userCornImg(userCornImg)
                .build();
    }

}