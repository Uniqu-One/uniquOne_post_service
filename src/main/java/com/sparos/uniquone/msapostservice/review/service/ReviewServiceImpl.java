package com.sparos.uniquone.msapostservice.review.service;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import com.sparos.uniquone.msapostservice.post.repository.IPostImgRepository;
import com.sparos.uniquone.msapostservice.post.repository.IPostRepository;
import com.sparos.uniquone.msapostservice.review.domain.Review;
import com.sparos.uniquone.msapostservice.review.dto.ReviewCreateDto;
import com.sparos.uniquone.msapostservice.review.repository.IReviewRepository;
import com.sparos.uniquone.msapostservice.review.utils.ReviewUtils;
import com.sparos.uniquone.msapostservice.trade.domain.Trade;
import com.sparos.uniquone.msapostservice.trade.repository.ITradeRepository;
import com.sparos.uniquone.msapostservice.util.feign.service.IUserConnect;
import com.sparos.uniquone.msapostservice.util.response.ExceptionCode;
import com.sparos.uniquone.msapostservice.util.response.UniquOneServiceException;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public JSONObject createReview(ReviewCreateDto reviewCreateDto) {
        JSONObject jsonObject = new JSONObject();
        Trade trade = iTradeRepository.findByIdAndIsReview(reviewCreateDto.getTradeId(), false)
                .orElseThrow(() -> UniquOneServiceException.of(ExceptionCode.NO_SUCH, "후기 작성 가능 한 거래가 아닙니다."));

        Review review = iReviewRepository.save(Review.builder()
                        .userId(reviewCreateDto.getUserId())
                        .post(iPostRepository.findById(reviewCreateDto.getPostId()).get())
                        .star(reviewCreateDto.getStar())
                        .dsc(reviewCreateDto.getDsc())
                        .build());

        trade.setReview(true);
        iTradeRepository.save(trade);

        jsonObject.put("result", "후기를 작성했습니다.");
        jsonObject.put("data", review); // todo 필요없으면 변경

        return jsonObject;
    }

    // 나의 콘 후기 조회
    @Override
    public JSONObject findMyCornReview(Long userId) {
        JSONObject jsonObject = new JSONObject();
        Optional<Corn> corn = iCornRepository.findByUserId(userId);

        if (corn.isPresent()){

            List<Long> postIds= iPostRepository.findIdByCornId(corn.get().getId())
                    .orElseThrow();
            List<Review> review = iReviewRepository.findByPostIdIn(postIds)
                    .orElseThrow();

            jsonObject.put("result", "나의 콘 후기를 조회했습니다.");
            jsonObject.put("data", review.stream().map(re ->
                            ReviewUtils.entityToReviewOutDto(
                                    re,
                                    iUserConnect.getUserInfo(re.getUserId()),
                                    iCornRepository.findImgUrlByUserId(re.getUserId()),
                                    iPostImgRepository.findUrlById(re.getPost().getId())))
            );

        }else {
            jsonObject.put("result", "콘이 존재하지 않습니다. 콘을 생성해주세요.");
            jsonObject.put("data", 0);
        }

        return jsonObject;
    }

    // 다른 유저 콘 후기 조회
    @Override
    public JSONObject findOtherCornReview(Long cornId) {
        JSONObject jsonObject = new JSONObject();
        Optional<Corn> corn = iCornRepository.findById(cornId);

        if (corn.isPresent()){

            List<Long> postIds= iPostRepository.findIdByCornId(corn.get().getId())
                    .orElseThrow();
            List<Review> reviews = iReviewRepository.findByPostIdIn(postIds)
                    .orElseThrow();

            jsonObject.put("result", "다른 유저의 콘 후기를 조회했습니다.");
            jsonObject.put("data", reviews.stream().map(re ->
                    ReviewUtils.entityToReviewOutDto(
                            re,
                            iUserConnect.getUserInfo(re.getUserId()),
                            iCornRepository.findImgUrlByUserId(re.getUserId()),
                            iPostImgRepository.findUrlById(re.getPost().getId())))
            );

        }else {
            jsonObject.put("result", "콘이 존재하지 않습니다.");
            jsonObject.put("data", 0);
        }

        return jsonObject;
    }

    // 작성 한 후기 조회
    @Override
    public JSONObject findMyReview(Long userId) {
        JSONObject jsonObject = new JSONObject();

        List<Review> reviews = iReviewRepository.findByUserId(userId)
                .orElseThrow();

        jsonObject.put("result", "작성 한 후기를 조회했습니다.");
        jsonObject.put("data", reviews.stream().map(re ->
                ReviewUtils.entityToReviewOutDto(
                        re,
                        iUserConnect.getUserInfo(re.getUserId()),
                        iCornRepository.findImgUrlByUserId(re.getUserId()),
                        iPostImgRepository.findUrlById(re.getPost().getId())))
        );

        return jsonObject;
    }
}

