package com.sparos.uniquone.msapostservice.eco.service;

import com.sparos.uniquone.msapostservice.eco.domain.Eco;
import com.sparos.uniquone.msapostservice.eco.dto.EcoCntDto;
import com.sparos.uniquone.msapostservice.eco.dto.EcoSumDto;
import com.sparos.uniquone.msapostservice.eco.repository.EcoRepositoryCustom;
import com.sparos.uniquone.msapostservice.eco.repository.IEcoRepository;
import com.sparos.uniquone.msapostservice.post.domain.PostCategory;
import com.sparos.uniquone.msapostservice.post.repository.IPostCategoryRepository;
import com.sparos.uniquone.msapostservice.post.repository.IPostRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Component
public class EcoServiceImpl implements IEcoService{

    private final IEcoRepository iEcoRepository;
    private final IPostCategoryRepository iPostCategoryRepository;
    private final EcoRepositoryCustom ecoRepositoryCustom;

    @Scheduled(cron = "0 0 0 * * *")
    @Override
    public void run() {
        // 거래에서 오늘 날짜에 맞는 리스트를 카테고리 별 카운트를 가져온다
        // 카테고리 별 탄소배출량 계산한다
        // 에코테이블에 넣는다 (일단 워터든 뭐든 똑같이 넣음음)
        EcoCntDto ecoCntDto = ecoRepositoryCustom.findCntByDate();
        List<PostCategory> postCategoryList = iPostCategoryRepository.findAll();

        double ecoCalculation= 0;

        for (PostCategory postCategory : postCategoryList) {
            if (postCategory.getId().equals(1l)) {
                ecoCalculation+= postCategory.getEco() * ecoCntDto.getOuterCnt();
            } else if (postCategory.getId().equals(2l)) {
                ecoCalculation+= postCategory.getEco() * ecoCntDto.getShirtCnt();
            } else if (postCategory.getId().equals(3l)) {
                ecoCalculation += postCategory.getEco() * ecoCntDto.getTShirtCnt();
            } else if (postCategory.getId().equals(4l)) {
                ecoCalculation += postCategory.getEco() * ecoCntDto.getMantomanCnt();
            } else if (postCategory.getId().equals(5l)) {
                ecoCalculation += postCategory.getEco() * ecoCntDto.getNeatCnt();
            } else if (postCategory.getId().equals(6l)) {
                ecoCalculation += postCategory.getEco() * ecoCntDto.getBlueJeansCnt();
            } else if (postCategory.getId().equals(7l)) {
                ecoCalculation += postCategory.getEco() * ecoCntDto.getJeansCnt();
            } else if (postCategory.getId().equals(8l)) {
                ecoCalculation += postCategory.getEco() * ecoCntDto.getSkirtCnt();
            } else if (postCategory.getId().equals(9l)) {
                ecoCalculation += postCategory.getEco() * ecoCntDto.getDressCnt();
            } else if (postCategory.getId().equals(10l)) {
                ecoCalculation += postCategory.getEco() * ecoCntDto.getSuitCnt();
            }
        }
        iEcoRepository.save(Eco.builder()
                        .regDate(ecoCntDto.getTradeRegDate())
                        .water(ecoCalculation * 1.32)
                        .carbon(ecoCalculation * 1.102)
                        .distance(ecoCalculation * 0.3)
                .build());
    }

    // 어제 탄소 절감 조회
    @Override
    public JSONObject findYesterdayEco() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", iEcoRepository.findByRegDate(LocalDate.now().minusDays(1).toString()));
        return jsonObject;
    }

    // 지금까지의 탄소 절감 합
    @Override
    public JSONObject findAllSumEco() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("data",  EcoSumDto.builder()
                        .carbonSum(iEcoRepository.findCarbonSumEco())
                        .distanceSum(iEcoRepository.findDistanceSumEco())
                        .waterSum(iEcoRepository.findWaterSumEco())
                .build());

        return jsonObject;
    }

    // 총 탄소 절감 조회
    @Override
    public JSONObject findAllEco() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", iEcoRepository.findAll());
        return jsonObject;
    }

    // 거래에서 어제 날짜 카테고리 별 카운트 가져오기 테스트
    @Override
    public JSONObject test() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", ecoRepositoryCustom.findCntByDate());
        return jsonObject;
    }

    // 카운트 카테고리 별 에코 계산 총 합 테스트
    @Override
    public JSONObject test1() {

        JSONObject jsonObject = new JSONObject();
        EcoCntDto ecoCntDto = ecoRepositoryCustom.findCntByDate();
        List<PostCategory> postCategoryList = iPostCategoryRepository.findAll();

        double e = 0;

        for (PostCategory postCategory : postCategoryList) {
            if (postCategory.getId().equals(1l)) {
                e += postCategory.getEco() * ecoCntDto.getOuterCnt();
            } else if (postCategory.getId().equals(2l)) {
                e += postCategory.getEco() * ecoCntDto.getShirtCnt();
            } else if (postCategory.getId().equals(3l)) {
                e += postCategory.getEco() * ecoCntDto.getTShirtCnt();
            } else if (postCategory.getId().equals(4l)) {
                e += postCategory.getEco() * ecoCntDto.getMantomanCnt();
            } else if (postCategory.getId().equals(5l)) {
                e += postCategory.getEco() * ecoCntDto.getNeatCnt();
            } else if (postCategory.getId().equals(6l)) {
                e += postCategory.getEco() * ecoCntDto.getBlueJeansCnt();
            } else if (postCategory.getId().equals(7l)) {
                e += postCategory.getEco() * ecoCntDto.getJeansCnt();
            } else if (postCategory.getId().equals(8l)) {
                e += postCategory.getEco() * ecoCntDto.getSkirtCnt();
            } else if (postCategory.getId().equals(9l)) {
                e += postCategory.getEco() * ecoCntDto.getDressCnt();
            } else if (postCategory.getId().equals(10l)) {
                e += postCategory.getEco() * ecoCntDto.getSuitCnt();
            }
        }

        jsonObject.put("data", e);
        return jsonObject;
    }
}
