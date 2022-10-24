package com.sparos.uniquone.msapostservice.post.dto.request.search;

import com.sparos.uniquone.msapostservice.post.domain.PostType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostSearchRequestDto {
    private String keyword;
    private List<String> colors = new ArrayList<>();
    private Long categoryId;
    private List<String> conditions = new ArrayList<>();
    private PostType postType;
    private Long LookId;
}
