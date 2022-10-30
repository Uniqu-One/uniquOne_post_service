package com.sparos.uniquone.msapostservice.admin.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AdminBoardResponseListDto {
    private List<AdminBoardResponseDto> result = new ArrayList<>();
    private boolean hasNext;

    public AdminBoardResponseListDto(List<AdminBoardResponseDto> result ,boolean hasNext){
        this.result = result;
        this.hasNext = hasNext;
    }
}
