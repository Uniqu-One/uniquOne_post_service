package com.sparos.uniquone.msapostservice.offer.service;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import com.sparos.uniquone.msapostservice.noti.domain.NotiType;
import com.sparos.uniquone.msapostservice.noti.service.IEmitterService;
import com.sparos.uniquone.msapostservice.offer.domain.Offer;
import com.sparos.uniquone.msapostservice.offer.domain.OfferType;
import com.sparos.uniquone.msapostservice.offer.domain.OfferUtils;
import com.sparos.uniquone.msapostservice.offer.dto.*;
import com.sparos.uniquone.msapostservice.offer.repository.IOfferRepository;
import com.sparos.uniquone.msapostservice.offer.repository.OfferRepositoryCustom;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.post.repository.IPostImgRepository;
import com.sparos.uniquone.msapostservice.post.repository.IPostRepository;
import com.sparos.uniquone.msapostservice.util.feign.dto.ChatRoomDto;
import com.sparos.uniquone.msapostservice.util.feign.dto.ChatRoomType;
import com.sparos.uniquone.msapostservice.util.feign.service.IChatConnect;
import com.sparos.uniquone.msapostservice.util.feign.service.IUserConnect;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import com.sparos.uniquone.msapostservice.util.response.ExceptionCode;
import com.sparos.uniquone.msapostservice.util.response.UniquOneServiceException;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements IOfferService{

    private final IOfferRepository iOfferRepository;
    private final IPostRepository iPostRepository;
    private final IPostImgRepository iPostImgRepository;
    private final ICornRepository iCornRepository;
    private final OfferRepositoryCustom offerRepositoryCustom;
    private final IEmitterService iEmitterService;
    private final IChatConnect iChatConnect;
    private final IUserConnect iUserConnect;

    // ?????? ?????????
    @Override
    public JSONObject sendOffer(OfferInputDto offerInputDto, HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();

        Post post = iPostRepository.findById(offerInputDto.getPostId())
                .orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED));

        Offer offer = iOfferRepository.save(OfferUtils.offerInputDtoToEntity(offerInputDto, post, request));

        iEmitterService.send(post.getCorn().getUserId(), offer, NotiType.OFFER);

        jsonObject.put("data", offer);

        return jsonObject;
    }

    // ?????? ?????? ?????? ??????
    @Override
    public JSONObject findCornOffer(HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();
        Long userId = JwtProvider.getUserPkId(request);

        //corn id ?????? post id list -> offer
        Corn corn = iCornRepository.findByUserId(userId)
                .orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED));

        List<Long> postIds = iPostRepository.findIdByCornId(corn.getId());
        if (postIds.isEmpty())
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED);

        List<OfferCntDto> offerCntDtos = offerRepositoryCustom.findCntByPostIdIn(postIds);
        if (offerCntDtos.isEmpty())
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED);

        jsonObject.put("data", offerCntDtos.stream().map(offer ->
                OfferUtils.entityToOfferOutDto(offer, iPostRepository.findPriceById(offer.getPostId()), iPostImgRepository.findUrlByPostId(offer.getPostId())))
        );

        return jsonObject;
    }

    // ?????? ?????? ?????? ?????? ??????
    @Override
    public JSONObject findCornOfferDetail(Long postId, HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();
        Long userId = JwtProvider.getUserPkId(request);

        List<Offer> offers = iOfferRepository.findByPostId(postId);
        if (offers.isEmpty())
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED);

        List<OfferDetailIndividualOutDto> offerDetailIndividualOutDto = offers.stream().map(offer -> {
                    String chatRoomId = "";
                    if (offer.getOfferType().equals(OfferType.ACCEPT)){
                        chatRoomId = iChatConnect.offerChat(offer.getPost().getId(), userId, offer.getUserId());
                    }

                    return OfferUtils.entityToOfferDetailIndividualOutDto(
                            offer,
                            iCornRepository.findImgUrlByUserId(offer.getUserId()), offer.getUserId(),
                            iUserConnect.getNickName(offer.getUserId()),
                            chatRoomId);
                }).collect(Collectors.toList());

        OfferCntDto offerCntDto = offerRepositoryCustom.findCntByPostId(postId)
                .orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED));

        jsonObject.put("data", OfferUtils.dtoToOfferDetailOutDto(offerCntDto, iPostRepository.findPriceById(postId), iPostImgRepository.findUrlByPostId(postId), offerDetailIndividualOutDto));

        return jsonObject;
    }

    // ????????? ?????? ?????? ??????
    @Override
    public JSONObject findMyOffer(HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();
        Long userId = JwtProvider.getUserPkId(request);

        List<Offer> offers = iOfferRepository.findByUserIdOrderByRegDateDesc(userId);
        if (offers.isEmpty())
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED);

        jsonObject.put("data", offers.stream().map(offer ->
                OfferUtils.entityToOfferMyOutDto(offer, iPostImgRepository.findUrlByPostId(offer.getPost().getId())))
        );
        return jsonObject;
    }

    // ?????? ??????
    @Override
    public JSONObject offerChecked(OfferCheckedInPutDto offerCheckedInPutDto, HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();
        Offer offer = iOfferRepository.findById(offerCheckedInPutDto.getOfferId())
              .orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED));

        offer.modOfferType(offerCheckedInPutDto.getOfferType());

        // ???????????? ????????? ?????? ????????? ?????? -> ?????? noti ???????????? save -> ????????? id ??????
        if (offer.getOfferType().equals(OfferType.ACCEPT)) {
            ChatRoomDto chatRoomDto = ChatRoomDto.builder()
                    .postId(offer.getPost().getId())
                    .receiverId(offer.getUserId())
                    .postPrice(offer.getPost().getPrice())
                    .offerPrice(offer.getPrice())
                    .chatType(ChatRoomType.SELLER)
                    .build();
            iChatConnect.offerChat(chatRoomDto, JwtProvider.getTokenFromRequestHeader(request));
            iEmitterService.send(offer.getUserId(), offer, NotiType.OFFER_ACCEPT);
        } else {

            iEmitterService.send(offer.getUserId(), offer, NotiType.OFFER_REFUSE);
        }

        jsonObject.put("data", offer);

        return jsonObject;
    }
}
