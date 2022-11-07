package com.sparos.uniquone.msapostservice.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRecommendResponseDto {
    private Long postId;
    private String postImgUrl;
    private String cornImgUrl;
    private String userNickName;
    private Boolean isCool;
    private Boolean isFollow;
    private Integer uniStar;

    private Long cornId;
    public PostRecommendResponseDto(Long postId, String postImgUrl) {
        this.postId = postId;
        this.postImgUrl = postImgUrl;
    }
    public PostRecommendResponseDto(Long postId, String postImgUrl, String cornImgUrl, String userNickName) {
        this.postId = postId;
        this.postImgUrl = postImgUrl;
        this.cornImgUrl = cornImgUrl;
        this.userNickName = userNickName;
    }

    public PostRecommendResponseDto(Long postId, String postImgUrl, String cornImgUrl, String userNickName, Long cornId) {
        this.postId = postId;
        this.postImgUrl = postImgUrl;
        this.cornImgUrl = cornImgUrl;
        this.userNickName = userNickName;
        this.cornId = cornId;
    }

    public PostRecommendResponseDto(Long postId, String postImgUrl , String cornImgUrl, String userNickName, boolean isCool, boolean isFollow, Integer uniStar) {
        this.postId = postId;
        this.postImgUrl = postImgUrl;
        this.cornImgUrl = cornImgUrl;
        this.userNickName = userNickName;
        this.isCool = isCool;
        this.isFollow = isFollow;
        this.uniStar = uniStar;
    }

    public PostRecommendResponseDto(Long postId, String postImgUrl, Boolean isCool) {
        this.postId = postId;
        this.postImgUrl = postImgUrl;
        this.isCool = isCool;
    }
}
