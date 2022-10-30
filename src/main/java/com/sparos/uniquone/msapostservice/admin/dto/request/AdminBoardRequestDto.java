package com.sparos.uniquone.msapostservice.admin.dto.request;

import com.sparos.uniquone.msapostservice.admin.domain.BoardType;
import lombok.Data;

@Data
public class AdminBoardRequestDto {
    private String boardType;
    private String title;
    private String subTitle;
    private String imgUrl;
}
