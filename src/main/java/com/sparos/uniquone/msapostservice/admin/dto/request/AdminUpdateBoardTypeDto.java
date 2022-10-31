package com.sparos.uniquone.msapostservice.admin.dto.request;

import com.sparos.uniquone.msapostservice.admin.domain.BoardType;
import lombok.Data;

@Data
public class AdminUpdateBoardTypeDto {
    private BoardType boardType;
}
