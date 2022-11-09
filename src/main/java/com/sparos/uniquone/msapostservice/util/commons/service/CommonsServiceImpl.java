package com.sparos.uniquone.msapostservice.util.commons.service;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import com.sparos.uniquone.msapostservice.util.commons.dto.response.CommonsUserResponseDto;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommonsServiceImpl implements CommonsService {

    private final ICornRepository cornRepository;

    @Override
    public CommonsUserResponseDto getCommonsUserInfoByToken(HttpServletRequest request) {
        //토큰 검증
        Long userPkId = JwtProvider.getUserPkId(request);

        //콘 찾기
        Optional<Corn> oCorn = cornRepository.findByUserId(userPkId);

        if(oCorn.isPresent()){
            return new CommonsUserResponseDto(userPkId,oCorn.get().getUserNickName(),oCorn.get().getId(),oCorn.get().getImgUrl());
        }

        return new CommonsUserResponseDto(userPkId, null);

//        Corn corn = cornRepository.findByUserId(userPkId).orElseThrow(() ->
//                new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.OK)
//        );
//
//        cornRepository.findByUserId(userPkId).ifPresent(
//            return new CommonsUserResponseDto(userPkId,corn.getId());
//        );

        //다넣고 반환.

    }
}
