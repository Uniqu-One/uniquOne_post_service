package com.sparos.uniquone.msapostservice.qna.domain;

import com.sparos.uniquone.msapostservice.qna.dto.QnAAdminOutDto;
import com.sparos.uniquone.msapostservice.qna.dto.QnADetailOutDto;
import com.sparos.uniquone.msapostservice.qna.dto.QnAOutDto;
import com.sparos.uniquone.msapostservice.qna.dto.QuestionInputDto;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;

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
                .qRegDate(qna.getQRegDate().format(DateTimeFormatter.ofPattern("yyyy년M월dd일 hh:mm")))
                .isAnswer(qna.getAnswer() == null? false : true)
                .build();
    }


    public static QnAAdminOutDto entityToQnAAdminOutDto(QnA qna) {
        return QnAAdminOutDto.builder()
                .qnaId(qna.getId())
                .question(qna.getQuestion())
                .questionType(qna.getQuestionType())
                .qRegDate(qna.getQRegDate().format(DateTimeFormatter.ofPattern("yyyy년M월dd일 hh:mm")))
                .answer(qna.getAnswer())
                .isAnswer(qna.getAnswer() == null? false : true)
                .build();
    }


    public static QnADetailOutDto entityToQnADetailOutDto(QnA qna, String cornImg) {
        Boolean isAnswer = qna.getAnswer() == null? false : true;

        return QnADetailOutDto.builder()
                .qnaId(qna.getId())
                .question(qna.getQuestion())
                .questionType(qna.getQuestionType())
                .qRegDate(qna.getQRegDate().format(DateTimeFormatter.ofPattern("yyyy년M월dd일 hh:mm")))
                .cornImg(cornImg)
                .isAnswer(isAnswer)
                .answer(isAnswer==true? qna.getAnswer() : null)
                .aRegDate(isAnswer == true? qna.getARegDate().format(DateTimeFormatter.ofPattern("yyyy년M월dd일 hh:mm")) : null)
                .adminImg("https://uniquoneimg.s3.ap-northeast-2.amazonaws.com/img/KakaoTalk_20221017_114329237.png") // 어드민 기본 이미지
                .build();
    }

}
