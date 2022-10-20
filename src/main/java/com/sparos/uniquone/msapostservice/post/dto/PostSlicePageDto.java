package com.sparos.uniquone.msapostservice.post.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
public class PostSlicePageDto<T> {
    private List<T> content;
    private Integer pageNumber;
    private Boolean pageFirst;
    private Boolean pageLast;
}
