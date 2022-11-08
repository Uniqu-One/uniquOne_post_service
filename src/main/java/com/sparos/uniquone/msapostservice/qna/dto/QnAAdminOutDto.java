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
    private String userCornImg;
    private String userNickName;
    private String question;
    private QuestionType questionType;
    private String answer;
    private String qRegDate;
    private String aRegDate;

    private Boolean isAnswer;

}
