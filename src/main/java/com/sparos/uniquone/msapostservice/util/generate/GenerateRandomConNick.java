package com.sparos.uniquone.msapostservice.util.generate;

import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GenerateRandomConNick {

    private final ICornRepository cornRepository;

    private final int SHORT_ID_LENGTH = 7;

    private static List<String> firstWords = Arrays.asList("헤롱헤롱한", "당도높은", "너그러운", "성질급한", "빛갈좋은", "매우상한", "싱싱한", "벌레먹은"
            , "1등급", "맛있는", "맛없는", "예쁜", "화난", "귀여운", "배고픈", "철학적인", "현학적인", "슬픈", "푸른", "비싼", "밝은", "심드렁한", "바보같은", "멍청한"
            , "똑똑한", "배긁는", "안경쓴");
    private static List<String> secondWords = Arrays.asList("참외","로즈애플","포도","피망","가지","토마토","머스켓","리치","모과","구기자","브로콜리","구아바","무화과","깔라만시","메론", "모과", "파파야", "한라봉", "석류", "코코넛", "야자", "두리안", "망고스틴", "슈가애플", "복숭아", "망고", "자두", "체리", "딸기"
            , "감", "오렌지", "사과", "자몽", "키위", "파인애플", "망고", "바나나", "라임", "레몬", "아보카도", "수박", "두리안","앵두");

    private String shuffle() {
        Collections.shuffle(firstWords);
        Collections.shuffle(secondWords);

        return firstWords.get(0) + secondWords.get(0) + "의 숖";
    }

    private String randomAlphanumeric() {
        return RandomStringUtils.randomAlphanumeric(SHORT_ID_LENGTH) + "의 숖";
    }


    public String generate() {
        //닉네임 생성 해주는애 경우의 수가 낮아서 해주는 조치.
        String nickname = shuffle();
        int checkCnt = 0;
        while (cornRepository.existsByTitle(nickname)) {
            nickname = shuffle();
            if (checkCnt >= 5) {
                checkCnt = 0;
                String randomId = randomAlphanumeric();

                while (cornRepository.existsByTitle(randomId)) {
                    if (checkCnt >= 5) {
                        throw new RuntimeException();
                    }
                    randomId = randomAlphanumeric();
                    checkCnt++;
                }

                nickname = randomId;
            }
            checkCnt++;
        }
        return nickname;
    }
}
