package com.sparos.uniquone.msapostservice.eco.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EcoSumDto {

    private Double water;
    private Double carbon;
    private Double distance;

}
