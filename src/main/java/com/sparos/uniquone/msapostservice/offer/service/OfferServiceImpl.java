package com.sparos.uniquone.msapostservice.offer.service;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import com.sparos.uniquone.msapostservice.offer.domain.Offer;
import com.sparos.uniquone.msapostservice.offer.domain.OfferUtils;
import com.sparos.uniquone.msapostservice.offer.dto.OfferCntDto;
import com.sparos.uniquone.msapostservice.offer.dto.OfferInputDto;
import com.sparos.uniquone.msapostservice.offer.repository.IOfferRepository;
import com.sparos.uniquone.msapostservice.offer.repository.OfferRepositoryCustom;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.post.repository.IPostImgRepository;
import com.sparos.uniquone.msapostservice.post.repository.IPostRepository;
import com.sparos.uniquone.msapostservice.qna.domain.QnA;
import com.sparos.uniquone.msapostservice.qna.domain.QnAUtils;
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
public class OfferServiceImpl implements IOfferService{

    private final IOfferRepository iOfferRepository;
    private final IPostRepository iPostRepository;
    private final IPostImgRepository iPostImgRepository;
    private final ICornRepository iCornRepository;
    private final OfferRepositoryCustom offerRepositoryCustom;

    // 오퍼 보내기
    @Override
    public JSONObject sendOffer(OfferInputDto offerInputDto, HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();

        Post post = iPostRepository.findById(offerInputDto.getPostId())
                .orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED));

        Offer offer = iOfferRepository.save(OfferUtils.offerInputDtoToEntity(offerInputDto, post, request));
        jsonObject.put("data", offer);

        return jsonObject;
    }

    // 콘이 받은 오퍼 조회
    @Override
    public JSONObject findCornOffer(HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();
        Long userId = JwtProvider.getUserPkId(request);

        //corn id 조회 post id list -> offer
        Corn corn = iCornRepository.findByUserId(userId)
                .orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED));

        List<Long> postIds = iPostRepository.findIdByCornId(corn.getId());
        if (postIds.isEmpty())
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED);

        List<OfferCntDto> offerCntDtos = offerRepositoryCustom.findCntByPostIdIn(postIds);
        if (offerCntDtos.isEmpty())
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED);

        jsonObject.put("data", offerCntDtos.stream().map(offer ->
                OfferUtils.entityToOfferOutDto(offer, iPostImgRepository.findUrlByPostId(offer.getPostId())))
        );

        return jsonObject;
    }

    // 콘이 받은 오퍼 상세 조회
    @Override
    public JSONObject findCornOfferDetail(Long postId, HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();
        /*Long userId = JwtProvider.getUserPkId(request);

        List<Offer> offers = iOfferRepository.findByPostId(postId);
        if (offers.isEmpty())
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED);

        jsonObject.put("data", offers.stream().map(offer ->
                OfferUtils.entityToOfferOutDto(offer, iPostImgRepository.findUrlByPostId(offer.getPostId())))
        );
*/
        return jsonObject;
    }
}
