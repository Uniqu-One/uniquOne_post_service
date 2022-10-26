package com.sparos.uniquone.msapostservice.offer.domain;

import com.sparos.uniquone.msapostservice.offer.dto.*;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.qna.domain.QnA;
import com.sparos.uniquone.msapostservice.qna.dto.QnADetailOutDto;
import com.sparos.uniquone.msapostservice.qna.dto.QnAOutDto;
import com.sparos.uniquone.msapostservice.qna.dto.QuestionInputDto;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    public static OfferDetailIndividualOutDto entityToOfferDetailIndividualOutDto(Offer offer, String cornImgUrl, Long userId, String userNickName) {
        return OfferDetailIndividualOutDto.builder()
                .offerId(offer.getId())
                .cornImg(cornImgUrl)
                .userId(userId)
                .userNickName(userNickName)
                .offerRegDate("등록일")
                .offerCheckDate("확인일")
                .offerPrice(offer.getPrice())
                .offerType(offer.getOfferType())
                .build();
    }


    public static OfferDetailOutDto dtoToOfferDetailOutDto(OfferCntDto offerCntDto, String postImgUrl, List<OfferDetailIndividualOutDto> offerDetailIndividualOutDto) {
        return OfferDetailOutDto.builder()
                .postId(offerCntDto.getPostId())
                .postImg(postImgUrl)
                .postPrice(offerCntDto.getPrice())
                .waitingCnt(offerCntDto.getWaitingCnt())
                .acceptCount(offerCntDto.getAcceptCount())
                .refuseCount(offerCntDto.getRefuseCount())
                .offerDetailIndividualOutDtos(offerDetailIndividualOutDto)
                .build();
    }

    public static OfferMyOutDto entityToOfferMyOutDto(Offer offer, String postImgUrl) {
        return OfferMyOutDto.builder()
                .postId(offer.getPost().getId())
                .postImg(postImgUrl)
                .postPrice(offer.getPrice())
                .postPrice(offer.getWaitingCnt())
                .offerRegDate("등록일")
                .offerCheckDate("확인일")
                .offerPrice(offer.getPrice())
                .offerType(offer.getOfferType())
                .build();
    }

}

