package com.sparos.uniquone.msapostservice.offer.domain;

public enum OfferType {

    WAITING("대기"),
    ACCEPT("수락"),
    REFUSE("거절");

    String offerType;

    OfferType(String offerType){
        this.offerType =offerType;
    }

    public String value(){
        return offerType;
    }
}
