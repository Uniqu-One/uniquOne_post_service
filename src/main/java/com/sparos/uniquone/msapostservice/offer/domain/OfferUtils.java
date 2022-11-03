package com.sparos.uniquone.msapostservice.offer.domain;

import com.sparos.uniquone.msapostservice.offer.dto.*;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.util.feign.service.IChatConnect;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
public class OfferUtils {

    private final IChatConnect iChatConnect;

    public static Offer offerInputDtoToEntity(OfferInputDto offerInputDto, Post post, HttpServletRequest request) {
        return Offer.builder()
                .userId(JwtProvider.getUserPkId(request))
                .post(post)
                .price(offerInputDto.getPrice())
                .offerType(OfferType.WAITING)
                .build();
    }

    public static OfferOutDto entityToOfferOutDto(OfferCntDto offerCntDto, Integer price, String postImgUrl) {
        return OfferOutDto.builder()
                .postId(offerCntDto.getPostId())
                .postTitle(offerCntDto.getPostTitle())
                .postImg(postImgUrl)
                .price(price)
                .waitingCnt(offerCntDto.getWaitingCnt())
                .acceptCount(offerCntDto.getAcceptCount())
                .refuseCount(offerCntDto.getRefuseCount())
                .build();
    }

    public static OfferDetailIndividualOutDto entityToOfferDetailIndividualOutDto(Offer offer, String cornImgUrl, Long userId, String userNickName, String chatRoomId) {

        String offerCheckDate = "";

        if (!offer.getOfferType().equals(OfferType.WAITING))
            offerCheckDate = offer.getCheckDate().format(DateTimeFormatter.ofPattern("yy.MM.dd"));

        return OfferDetailIndividualOutDto.builder()
                .offerId(offer.getId())
                .cornImg(cornImgUrl)
                .userId(userId)
                .userNickName(userNickName)
                .offerRegDate(offer.getRegDate().format(DateTimeFormatter.ofPattern("yy.MM.dd")))
                .offerCheckDate(offerCheckDate)
                .offerPrice(offer.getPrice())
                .offerType(offer.getOfferType())
                .chatRoomId(chatRoomId)
                .build();
    }

    public static OfferDetailOutDto dtoToOfferDetailOutDto(OfferCntDto offerCntDto, String postImgUrl, List<OfferDetailIndividualOutDto> offerDetailIndividualOutDto) {

        return OfferDetailOutDto.builder()
                .postId(offerCntDto.getPostId())
                .postTitle(offerCntDto.getPostTitle())
                .postImg(postImgUrl)
                .postPrice(offerCntDto.getPrice())
                .waitingCnt(offerCntDto.getWaitingCnt())
                .acceptCount(offerCntDto.getAcceptCount())
                .refuseCount(offerCntDto.getRefuseCount())
                .offerDetailIndividualOutDtos(offerDetailIndividualOutDto)
                .build();
    }

    public static OfferMyOutDto entityToOfferMyOutDto(Offer offer, String postImgUrl) {

        String offerCheckDate = "";

        if (!offer.getOfferType().equals(OfferType.WAITING))
            offerCheckDate = offer.getCheckDate().format(DateTimeFormatter.ofPattern("yy.MM.dd"));

        return OfferMyOutDto.builder()
                .postId(offer.getPost().getId())
                .postTitle(offer.getPost().getTitle())
                .postImg(postImgUrl)
                .postPrice(offer.getPrice())
                .postPrice(offer.getWaitingCnt())
                .offerRegDate(offer.getRegDate().format(DateTimeFormatter.ofPattern("yy.MM.dd")))
                .offerCheckDate(offerCheckDate)
                .offerPrice(offer.getPrice())
                .offerType(offer.getOfferType())
                .build();
    }

}

