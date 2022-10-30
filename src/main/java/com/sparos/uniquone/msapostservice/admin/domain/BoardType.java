package com.sparos.uniquone.msapostservice.admin.domain;

public enum BoardType {
    ING("ING")
    ,CLOSE("CLOSE");

    String status;

    BoardType(String status){
        this.status = status;
    }

    public String value(){
        return status;
    }
}
