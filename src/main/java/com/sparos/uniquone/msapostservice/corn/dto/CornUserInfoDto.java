package com.sparos.uniquone.msapostservice.corn.dto;

import com.sparos.uniquone.msapostservice.follow.dto.IsFollowDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CornUserInfoDto {
    private CornInfoDto cornInfoDto;
    private IsFollowDto isFollowDto;
}
