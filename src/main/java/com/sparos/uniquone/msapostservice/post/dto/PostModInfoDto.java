package com.sparos.uniquone.msapostservice.post.dto;

import com.sparos.uniquone.msapostservice.post.domain.PostType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PostModInfoDto {
    private List<String> postImgList;
    private String dsc;
    private List<String> postTagNameList;
    private PostType postType;
    private String postCategoryName;
    private String conditions;
    private List<String> lookList;
    private List<String> color;
}
