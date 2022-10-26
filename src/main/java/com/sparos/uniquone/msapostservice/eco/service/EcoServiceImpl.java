package com.sparos.uniquone.msapostservice.eco.service;

import com.sparos.uniquone.msapostservice.eco.repository.IEcoRepository;
import com.sparos.uniquone.msapostservice.post.repository.IPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EcoServiceImpl implements IEcoService{

    private final IEcoRepository iEcoRepository;
    private final IPostRepository iPostRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @Override
    public void run() {
        // 거래에서 오늘 날짜에 맞는 리스트를 카테고리 별 카운트를 가져온다
        // 카테고리 별 탄소배출량 계산한다
        // 에코테이블에 넣는다 (일단 워터든 뭐든 똑같이 넣음음)







    }
}
