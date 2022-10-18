package com.sparos.uniquone.msapostservice.review.utils;

import com.sparos.uniquone.msapostservice.review.domain.Review;
import com.sparos.uniquone.msapostservice.review.dto.ReviewOutDto;

public class ReviewUtils {

    public static ReviewOutDto entityToReviewOutDto(Review review, String userNickname, String cornImg, String postImg) {

        return ReviewOutDto.builder()
                .reviewId(review.getId())
                .userId(review.getUserId())
                .userNickName(userNickname)
                .cornImg(cornImg)
                .postId(review.getPost().getId())
                .postImg(postImg)
                .star(review.getStar())
                .dsc(review.getDsc())
                .reviewRegDate(review.getRegDate())
                .build();
    }

}
