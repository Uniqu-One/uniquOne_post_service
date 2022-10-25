package com.sparos.uniquone.msapostservice.post.dto;

import com.sparos.uniquone.msapostservice.post.domain.PostType;
import com.sparos.uniquone.msapostservice.post.domain.ProductSize;
import lombok.Getter;

@Getter
public class PostInputDto {
    private String title;
    private String dsc;
    private String postTagLine;
    private PostType postType;
    private String postCategoryName;
    private String conditions;
    private String lookLine;
    private String color;
    private Integer price;
    private ProductSize productSize;
}
