package com.sparos.uniquone.msapostservice.admin.dto.response;

import lombok.Data;

@Data
public class ThisWeekUniqueOneResponseDto {
    private Long cornId;
    private String cornImgUrl;
    private String userNickName;
    private String postImgUrl;

    public ThisWeekUniqueOneResponseDto(Long cornId, String cornImgUrl, String userNickName, String postImgUrl) {
        this.cornId = cornId;
        this.cornImgUrl = cornImgUrl;
        this.userNickName = userNickName;
        this.postImgUrl = postImgUrl;
    }
}
