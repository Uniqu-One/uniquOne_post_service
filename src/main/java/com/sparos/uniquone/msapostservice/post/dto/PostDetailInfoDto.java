package com.sparos.uniquone.msapostservice.post.dto;

import com.sparos.uniquone.msapostservice.post.domain.PostType;
import com.sparos.uniquone.msapostservice.post.domain.ProductSize;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostDetailInfoDto {

    private List<String> imgUrlList;

    private String title;

    private String dsc;

    private List<String> postTagList;

    private Long postCategoryId;

    private PostType postType;

    private List<Long> LookId;

    private List<String> colorList;

    private ProductSize productSize;

    private String condition;

    private Boolean isCool;

    private String cornImgUrl;

    private String userNickName;

    private Integer uniStar;

    private Boolean isFollow;

    private Long cornId;

    private Integer price;

}
