package com.sparos.uniquone.msapostservice.review.domain;

import com.sparos.uniquone.msapostservice.review.dto.ReviewOutDto;

import java.time.format.DateTimeFormatter;

public class ReviewUtils {

    public static ReviewOutDto entityToReviewOutDto(Review review, String nickname, String cornImg, String postImg) {

        return ReviewOutDto.builder()
                .reviewId(review.getId())
                .userId(review.getUserId())
                .userNickName(nickname)
                .cornImg(cornImg)
                .postId(review.getPost().getId())
                .postImg(postImg)
                .star(review.getStar())
                .dsc(review.getDsc())
                .reviewRegDate(review.getRegDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .build();
    }



}
