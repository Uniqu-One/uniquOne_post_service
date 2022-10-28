package com.sparos.uniquone.msapostservice.eco.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EcoCntDto {

    private LocalDate tradeRegDate;

    private Long outerCnt;
    private Long shirtCnt;
    private Long tShirtCnt;
    private Long mantomanCnt;
    private Long neatCnt;
    private Long blueJeansCnt;
    private Long jeansCnt;
    private Long skirtCnt;
    private Long dressCnt;
    private Long suitCnt;

}
