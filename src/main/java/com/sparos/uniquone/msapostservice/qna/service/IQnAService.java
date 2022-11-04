package com.sparos.uniquone.msapostservice.qna.service;

import com.sparos.uniquone.msapostservice.qna.dto.AnswerInputDto;
import com.sparos.uniquone.msapostservice.qna.dto.QuestionInputDto;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;

public interface IQnAService {

    // 문의 등록
    JSONObject createQuestion(QuestionInputDto questionInputDto, HttpServletRequest request);

    // 나의 문의 내역 조회
    JSONObject findMyQnA(HttpServletRequest request);

    // 나의 문의 개별 조회
    JSONObject findMyDetailQnA(Long qnaId, HttpServletRequest request);

    // 모든 문의 내역 조회
    JSONObject findAllQnA();

    // 문의 상세 조회
    JSONObject findDetailQnA(Long qnaId);

    // 답변 등록
    JSONObject createAnswer(AnswerInputDto answerInputDto);

}
