package com.sparos.uniquone.msapostservice.noti.domain;

public enum NotiType {

    APPLY("알리미"),
    COOL("좋아요"),
    COMMENT("댓글"),
    FOLLOW("팔로우"),
    QNA("문의");

    String notiType;

    NotiType(String notiType) {
        this.notiType = notiType;
    }

    public String value() {
        return notiType;
    }
}
