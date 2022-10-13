package com.sparos.uniquone.msapostservice.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OtherProfilePostAllList {
    public List<PostListInfoDto> postListInfoDtoList;
}
