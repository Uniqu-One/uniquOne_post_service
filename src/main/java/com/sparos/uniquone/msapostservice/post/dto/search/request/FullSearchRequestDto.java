package com.sparos.uniquone.msapostservice.post.dto.search.request;

import com.sparos.uniquone.msapostservice.post.domain.PostType;
import lombok.Data;

import java.util.List;

@Data
public class FullSearchRequestDto {
    private String keyword;
    private List<String> colors;
    private Long categoryId;
    private List<String> conditions;
    private PostType postType;
    private Long LookId;
}
