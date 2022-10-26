package com.sparos.uniquone.msapostservice.offer.domain;

import com.sparos.uniquone.msapostservice.offer.dto.OfferCntDto;
import com.sparos.uniquone.msapostservice.offer.dto.OfferInputDto;
import com.sparos.uniquone.msapostservice.offer.dto.OfferOutDto;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.qna.domain.QnA;
import com.sparos.uniquone.msapostservice.qna.dto.QnADetailOutDto;
import com.sparos.uniquone.msapostservice.qna.dto.QnAOutDto;
import com.sparos.uniquone.msapostservice.qna.dto.QuestionInputDto;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;

public class OfferUtils {

    public static Offer offerInputDtoToEntity(OfferInputDto offerInputDto, Post post, HttpServletRequest request) {
        return Offer.builder()
                .userId(JwtProvider.getUserPkId(request))
                .post(post)
                .price(offerInputDto.getPrice())
                .offerType(OfferType.WAITING)
                .build();
    }

    public static OfferOutDto entityToOfferOutDto(OfferCntDto offerCntDto, String postImgUrl) {
        return OfferOutDto.builder()
                .postId(offerCntDto.getPostId())
                .postImg(postImgUrl)
                .price(offerCntDto.getPrice())
                .waitingCnt(offerCntDto.getWaitingCnt())
                .acceptCount(offerCntDto.getAcceptCount())
                .refuseCount(offerCntDto.getRefuseCount())
                .build();
    }
}
