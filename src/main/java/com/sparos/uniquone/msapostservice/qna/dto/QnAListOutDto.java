package com.sparos.uniquone.msapostservice.qna.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QnAListOutDto {

    private Integer totalPage;

    private List<QnAOutDto> qnAOutDtos;

}
