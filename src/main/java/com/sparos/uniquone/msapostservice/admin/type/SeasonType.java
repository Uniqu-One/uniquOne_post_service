package com.sparos.uniquone.msapostservice.admin.type;

public enum SeasonType {
    SPRING("SPRING"),
    SUMMER("SUMMER"),
    FALL("FALL"),
    WINTER("WINTER");

    String value;
    SeasonType(String value) {
        this.value = value;
    }
    public String value(){
        return value;
    }
//    public SeasonType valueOf(String value){
//        SeasonType(value)
//    }
}
