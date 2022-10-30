package com.sparos.uniquone.msapostservice.admin.dto.response;

import com.sparos.uniquone.msapostservice.admin.domain.BoardType;
import com.sparos.uniquone.msapostservice.admin.dto.request.AdminBoardRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminBoardResponseDto {

    private Long boardId;
    private Long updateUserId;
    private BoardType boardType;
    private String title;
    private String subTitle;
    private String imgUrl;

    public AdminBoardResponseDto(Long boardId,Long updateUserId ,BoardType boardType, String title, String subTitle, String imgUrl) {
        this.boardId = boardId;
        this.updateUserId = updateUserId;
        this.boardType = boardType;
        this.title = title;
        this.subTitle = subTitle;
        this.imgUrl = imgUrl;
    }
}
