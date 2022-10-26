package com.sparos.uniquone.msapostservice.post.dto.search.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchCornDto {
    private Long cornId;

    private String cornImgUrl;
    private String cornNickName;
    private String userNickName;
    private boolean isFollow;

    public SearchCornDto(Long cornId,String cornImgUrl ,String cornNickName, String userNickName) {
        this.cornId = cornId;
        this.cornImgUrl = cornImgUrl;
        this.cornNickName = cornNickName;
        this.userNickName = userNickName;
        this.isFollow = false;
    }


    public SearchCornDto(Long cornId,String cornImgUrl ,String cornNickName, String userNickName, boolean isFollow) {
        this.cornId = cornId;
        this.cornImgUrl = cornImgUrl;
        this.cornNickName = cornNickName;
        this.userNickName = userNickName;
        this.isFollow = isFollow;
    }
}
