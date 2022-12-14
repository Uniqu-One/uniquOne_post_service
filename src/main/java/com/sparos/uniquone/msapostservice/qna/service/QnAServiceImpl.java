package com.sparos.uniquone.msapostservice.qna.service;

import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;

import com.sparos.uniquone.msapostservice.noti.domain.Noti;
import com.sparos.uniquone.msapostservice.noti.domain.NotiType;
import com.sparos.uniquone.msapostservice.noti.service.IEmitterService;
import com.sparos.uniquone.msapostservice.noti.service.INotiService;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.post.domain.PostType;

import com.sparos.uniquone.msapostservice.qna.domain.QnA;
import com.sparos.uniquone.msapostservice.qna.domain.QnAUtils;
import com.sparos.uniquone.msapostservice.qna.dto.AnswerInputDto;
import com.sparos.uniquone.msapostservice.qna.dto.QnAListOutDto;
import com.sparos.uniquone.msapostservice.qna.dto.QnAOutDto;
import com.sparos.uniquone.msapostservice.qna.dto.QuestionInputDto;
import com.sparos.uniquone.msapostservice.qna.repository.IQnARepository;
import com.sparos.uniquone.msapostservice.util.feign.service.IUserConnect;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import com.sparos.uniquone.msapostservice.util.response.ExceptionCode;
import com.sparos.uniquone.msapostservice.util.response.UniquOneServiceException;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QnAServiceImpl implements IQnAService {

    private final IQnARepository iQnARepository;
    private final ICornRepository iCornRepository;
    private final IEmitterService iEmitterService;
    private final IUserConnect iUserConnect;

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
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED);

        jsonObject.put("data", qnas.stream().map(qna ->
                QnAUtils.entityToQnAOutDto(qna, iUserConnect.getNickName(qna.getUserId())))
        );

        return jsonObject;
    }

    // 나의 문의 개별 조회
    @Override
    public JSONObject findMyDetailQnA(Long qnaId, HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();
        QnA qna = iQnARepository.findByIdAndUserId(qnaId, JwtProvider.getUserPkId(request))
                .orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED));

        String cornImg = iCornRepository.findImgUrlByUserId(JwtProvider.getUserPkId(request));

        jsonObject.put("data", QnAUtils.entityToQnADetailOutDto(qna,
                cornImg == null? "https://uniquoneimg.s3.ap-northeast-2.amazonaws.com/img/KakaoTalk_20221014_140108315.png" : cornImg));

        return jsonObject;
    }

    // 모든 문의 내역 조회
    @Override
    public JSONObject findAllQnA(int pageNum) {

        JSONObject jsonObject = new JSONObject();

        Pageable pageable = PageRequest.of(pageNum - 1, 20, Sort.by(Sort.Direction.DESC, "qRegDate"));
        Page<QnA> qnaPages = iQnARepository.findAll(pageable);
        if (qnaPages.isEmpty()) {
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED);
        }

        List<QnAOutDto> qnAOutDtos= qnaPages.stream().map(qna ->
                        QnAUtils.entityToQnAOutDto(qna, iUserConnect.getNickName(qna.getUserId()))).collect(Collectors.toList());
        
        jsonObject.put("data", QnAListOutDto.builder()
                        .totalPage(qnaPages.getTotalPages())
                        .qnAOutDtos(qnAOutDtos)
                .build());

        return jsonObject;
    }

    // 문의 상세 조회
    @Override
    public JSONObject findDetailQnA(Long qnaId) {
        JSONObject jsonObject = new JSONObject();
        QnA qna = iQnARepository.findById(qnaId)
                .orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED));

        jsonObject.put("data", QnAUtils.entityToQnAAdminOutDto(
                qna, iCornRepository.findImgUrlByUserId(qna.getUserId()), iUserConnect.getNickName(qna.getUserId())));

        return jsonObject;
    }

    // 답변 등록
    @Override
    public JSONObject createAnswer(AnswerInputDto answerInputDto) {

        JSONObject jsonObject = new JSONObject();
        QnA qna = iQnARepository.findById(answerInputDto.getQnaId())
                .orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED));

        qna.setAnswer(answerInputDto.getAnswer());
        qna = iQnARepository.save(qna);

        jsonObject.put("data", qna);

        iEmitterService.send(qna.getUserId(), qna, NotiType.QNA);
        return jsonObject;
    }
}
