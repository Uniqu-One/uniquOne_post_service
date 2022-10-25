package com.sparos.uniquone.msapostservice.noti.domain;

public enum NotiType {

    COOL("좋아요"),
    COMMENT("댓글"),
    FOLLOW("팔로우"),
    QNA("문의"),
    OFFER("오퍼");

    String notiType;

    NotiType(String notiType) {
        this.notiType = notiType;
    }

    public String value() {
        return notiType;
    }
}
