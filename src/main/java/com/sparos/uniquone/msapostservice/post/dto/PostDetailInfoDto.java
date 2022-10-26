package com.sparos.uniquone.msapostservice.post.dto;

import com.sparos.uniquone.msapostservice.post.domain.PostType;
import com.sparos.uniquone.msapostservice.post.domain.ProductSize;

import java.util.List;

public class PostDetailInfoDto {

    private String title;

    private String desc;

    private List<String> postTagList;

    private Long postCategoryId;

    private PostType postType;

    private Long LookId;

    private String color;

    private ProductSize productSize;

    private String condition;

}
