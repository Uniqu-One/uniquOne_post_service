package com.sparos.uniquone.msapostservice.noti.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class NotiUtils {

    public static final int SEC = 60;
    public static final int MIN = 60;
    public static final int HOUR = 24;
    public static final int DAY = 30;
    public static final int MONTH = 12;

    public static String converter(LocalDateTime msgRegDate) {

        LocalDateTime now = LocalDateTime.now();
        Long diffTime = 0l;

        if (msgRegDate != null)
            diffTime = msgRegDate.until(now, ChronoUnit.SECONDS);

        String msg = null;

        if (diffTime < SEC) {
            // sec
            msg = diffTime + "초 전";
        } else if ((diffTime /= SEC) < MIN) {
            // min
            msg = diffTime + "분 전";
        } else if ((diffTime /= MIN) < HOUR) {
            // hour
            msg = (diffTime) + "시간 전";
        } else if ((diffTime /= HOUR) < DAY) {
            // day
            msg = (diffTime) + "일 전";
        } else if ((diffTime /= DAY) < MONTH) {
            // day
            msg = (diffTime) + "달 전";
        } else {
            msg = (diffTime) + "년 전";
        }
        return msg;

    }
}
