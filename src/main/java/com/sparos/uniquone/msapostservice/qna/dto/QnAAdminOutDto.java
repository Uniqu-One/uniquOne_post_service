package com.sparos.uniquone.msapostservice.qna.dto;

import com.sparos.uniquone.msapostservice.qna.domain.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QnAAdminOutDto {

    private Long qnaId;
    private String question;
    private QuestionType questionType;
    private String qRegDate;
    private String answer;

    private Boolean isAnswer;

}
