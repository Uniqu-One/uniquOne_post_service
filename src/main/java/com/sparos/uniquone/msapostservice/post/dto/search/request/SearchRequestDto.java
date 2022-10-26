package com.sparos.uniquone.msapostservice.post.dto.search.request;

import com.sparos.uniquone.msapostservice.post.domain.PostType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class SearchRequestDto {
    @NotEmpty
    private String keyword;
    private List<String> colors = new ArrayList<>();
    private Long categoryId;
    private List<String> conditions = new ArrayList<>();
    private PostType postType;
    private List<Long> looks = new ArrayList<>();
}
