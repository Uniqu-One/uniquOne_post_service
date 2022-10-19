package com.sparos.uniquone.msapostservice.qna.domain;

import com.sparos.uniquone.msapostservice.qna.dto.QnAOutDto;
import com.sparos.uniquone.msapostservice.qna.dto.QuestionInputDto;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;

import javax.servlet.http.HttpServletRequest;

public class QnAUtils {

    public static QnA questionInputDtoToEntity(QuestionInputDto questionInputDto, HttpServletRequest request) {

        return QnA.builder()
                .userId(JwtProvider.getUserPkId(request))
                .question(questionInputDto.getQuestion())
                .questionType(questionInputDto.getQuestionType())
                .build();
    }

    public static QnAOutDto entityToQnAOutDto(QnA qna) {

        return QnAOutDto.builder()
                .qnaId(qna.getId())
                .question(qna.getQuestion())
                .questionType(qna.getQuestionType())
                .qRegDate(qna.getQRegDate())
                .isAnswer(qna.getAnswer() == null? false : true)
                .build();
    }
}
