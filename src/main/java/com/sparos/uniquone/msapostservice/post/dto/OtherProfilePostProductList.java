package com.sparos.uniquone.msapostservice.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OtherProfilePostProductList {
    public List<PostListInfoDto> postSaleListInfoDtoList;
    public List<PostListInfoDto> postSoldOutListInfoDtoList;
    public List<PostListInfoDto> postDiscontinuedListInfoDtoList;
}
