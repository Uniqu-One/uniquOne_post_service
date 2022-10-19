package com.sparos.uniquone.msapostservice.qna.service;

import com.sparos.uniquone.msapostservice.qna.dto.QuestionInputDto;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;

public interface IQnAService {

    // 문의 등록
    JSONObject createQuestion(QuestionInputDto questionInputDto, HttpServletRequest request);

    // 나의 문의 리스트 조회
    JSONObject findMyQnA(HttpServletRequest request);

}
