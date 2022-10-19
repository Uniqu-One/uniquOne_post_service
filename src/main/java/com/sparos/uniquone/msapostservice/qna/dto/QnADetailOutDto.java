package com.sparos.uniquone.msapostservice.qna.dto;

import com.sparos.uniquone.msapostservice.qna.domain.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QnADetailOutDto {

    private Long qnaId;
    private String question;
    private QuestionType questionType;
    private LocalDateTime qRegDate;

    private String cornImg;

    private Boolean isAnswer;
    private String answer;
    private String adminImg;
    private LocalDateTime aRegDate;

}
