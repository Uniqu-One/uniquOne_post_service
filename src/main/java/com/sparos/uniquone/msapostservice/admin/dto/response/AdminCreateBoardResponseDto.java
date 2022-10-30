package com.sparos.uniquone.msapostservice.admin.dto.response;

import com.sparos.uniquone.msapostservice.admin.domain.BoardType;
import lombok.Data;

@Data
public class AdminCreateBoardResponseDto {

    private Long writeId;
    private String title;
    private String subTitle;
    private String imgUrl;
    private BoardType boardType;

    public AdminCreateBoardResponseDto(Long writeId, String title, String subTitle, String imgUrl, BoardType boardType) {
        this.writeId = writeId;
        this.title = title;
        this.subTitle = subTitle;
        this.imgUrl = imgUrl;
        this.boardType = boardType;
    }
}
