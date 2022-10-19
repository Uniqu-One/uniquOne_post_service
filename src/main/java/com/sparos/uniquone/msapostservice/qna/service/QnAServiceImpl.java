package com.sparos.uniquone.msapostservice.qna.service;

import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.post.domain.PostType;
import com.sparos.uniquone.msapostservice.qna.domain.QnA;
import com.sparos.uniquone.msapostservice.qna.domain.QnAUtils;
import com.sparos.uniquone.msapostservice.qna.dto.QuestionInputDto;
import com.sparos.uniquone.msapostservice.qna.repository.IQnARepository;
import com.sparos.uniquone.msapostservice.trade.domain.Trade;
import com.sparos.uniquone.msapostservice.trade.domain.TradeUtils;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import com.sparos.uniquone.msapostservice.util.response.ExceptionCode;
import com.sparos.uniquone.msapostservice.util.response.UniquOneServiceException;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QnAServiceImpl implements IQnAService {

    private final IQnARepository iQnARepository;

    // 문의 등록
    @Override
    public JSONObject createQuestion(QuestionInputDto questionInputDto, HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();

        QnA qnA = iQnARepository.save(QnAUtils.questionInputDtoToEntity(questionInputDto, request));
        jsonObject.put("data", qnA);

        return jsonObject;
    }

    // 나의 문의 리스트 조회
    @Override
    public JSONObject findMyQnA(HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();
        List<QnA> qnas = iQnARepository.findByUserId(JwtProvider.getUserPkId(request));

        if (qnas.isEmpty())
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION);

        jsonObject.put("data", qnas.stream().map(qna ->
                QnAUtils.entityToQnAOutDto(qna))
        );

        return jsonObject;
    }






}
