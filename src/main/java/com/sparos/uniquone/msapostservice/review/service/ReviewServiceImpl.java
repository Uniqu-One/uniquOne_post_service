package com.sparos.uniquone.msapostservice.review.service;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import com.sparos.uniquone.msapostservice.post.repository.IPostImgRepository;
import com.sparos.uniquone.msapostservice.post.repository.IPostRepository;
import com.sparos.uniquone.msapostservice.review.domain.Review;
import com.sparos.uniquone.msapostservice.review.dto.ReviewCreateDto;
import com.sparos.uniquone.msapostservice.review.repository.IReviewRepository;
import com.sparos.uniquone.msapostservice.review.domain.ReviewUtils;
import com.sparos.uniquone.msapostservice.trade.domain.Trade;
import com.sparos.uniquone.msapostservice.trade.repository.ITradeRepository;
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

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements IReviewService {

    private final IReviewRepository iReviewRepository;
    private final ITradeRepository iTradeRepository;
    private final IPostRepository iPostRepository;
    private final IPostImgRepository iPostImgRepository;
    private final ICornRepository iCornRepository;
    private final IUserConnect iUserConnect;

    // 후기 작성
    @Override
    public JSONObject createReview(ReviewCreateDto reviewCreateDto, HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();
        Trade trade = iTradeRepository.findByIdAndIsReview(reviewCreateDto.getTradeId(), false)
                .orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED));

        Review review = iReviewRepository.save(
                Review.builder()
                        .userId(JwtProvider.getUserPkId(request))
                        .post(iPostRepository.findById(reviewCreateDto.getPostId()).get())
                        .star(reviewCreateDto.getStar())
                        .dsc(reviewCreateDto.getDsc())
                        .build()
        );

        trade.modIsReview(true);
        iTradeRepository.save(trade);

        jsonObject.put("data", review); // todo 필요없으면 변경

        return jsonObject;
    }

    // 나의 콘 후기 조회
    @Override
    public JSONObject findMyCornReview(HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();
        Corn corn = iCornRepository.findByUserId(JwtProvider.getUserPkId(request))
                .orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED));

        List<Long> postIds= iPostRepository.findIdByCornId(corn.getId());

        if (postIds.isEmpty())
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED);

        List<Review> reviews = iReviewRepository.findByPostIdIn(postIds);

        if (reviews.isEmpty())
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED);

        jsonObject.put("data", reviews.stream().map(re ->
                        ReviewUtils.entityToReviewOutDto(
                                re,
                                iUserConnect.getUserNickName(re.getUserId()).getNickname(),
                                iCornRepository.findImgUrlByUserId(re.getUserId()),
                                iPostImgRepository.findUrlByPostId(re.getPost().getId())))
        );

        return jsonObject;
    }

    // 다른 유저 콘 후기 조회
    @Override
    public JSONObject findOtherCornReview(Long cornId, HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();
        Corn corn = iCornRepository.findById(cornId)
                .orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED));

        List<Long> postIds= iPostRepository.findIdByCornId(corn.getId());
        if (postIds.isEmpty())
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED);

        List<Review> reviews = iReviewRepository.findByPostIdIn(postIds);
        if (reviews.isEmpty())
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED);

        jsonObject.put("data", reviews.stream().map(re ->
                ReviewUtils.entityToReviewOutDto(
                        re,
                        iUserConnect.getUserNickName(re.getUserId()).getNickname(),
                        iCornRepository.findImgUrlByUserId(re.getUserId()),
                        iPostImgRepository.findUrlByPostId(re.getPost().getId())))
        );

        return jsonObject;
    }

    // 작성 한 후기 조회
    @Override
    public JSONObject findMyReview(HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();
        List<Review> reviews = iReviewRepository.findByUserId(JwtProvider.getUserPkId(request));

        if (reviews.isEmpty())
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED);

        jsonObject.put("data", reviews.stream().map(re ->
                ReviewUtils.entityToReviewOutDto(
                        re,
                        iUserConnect.getUserNickName(re.getUserId()).getNickname(),
                        iCornRepository.findImgUrlByUserId(re.getUserId()),
                        iPostImgRepository.findUrlByPostId(re.getPost().getId())))
        );

        return jsonObject;
    }
}

