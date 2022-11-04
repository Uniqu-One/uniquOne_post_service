package com.sparos.uniquone.msapostservice.admin.type;

public enum SeasonType {
    SPRING("SPRING"),
    SUMMER("SUMMER"),
    FALL("FALL"),
    WINTER("WINTER"),
    OTC_1("OTC_1"),
    OTC_2("OTC_2"),
    OTC_3("OTC_3"),
    OTC_4("OTC_4"),

    ;

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
