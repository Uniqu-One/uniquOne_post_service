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
public class QnAOutDto {

    private Long qnaId;
    private String userNickname;
    private String question;
    private QuestionType questionType;
    private String qRegDate;

    private Boolean isAnswer;

}
