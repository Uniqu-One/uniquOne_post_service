package com.sparos.uniquone.msapostservice.eco.repository;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparos.uniquone.msapostservice.eco.dto.EcoCntDto;
import com.sparos.uniquone.msapostservice.eco.dto.EcoSumDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import static com.sparos.uniquone.msapostservice.trade.domain.QTrade.trade;

@RequiredArgsConstructor
@Repository
@Log4j2
public class EcoRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public EcoCntDto findCntByDate() {

        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})",
                trade.regDate,
                ConstantImpl.create("%Y-%m-%d")
        );

        return jpaQueryFactory
                .select(Projections.fields(EcoCntDto.class,
                        formattedDate.as("tradeRegDate"),
                        trade.outerCnt.as("outerCnt"),
                        trade.shirtCnt.as("shirtCnt"),
                        trade.tShirtCnt.as("tShirtCnt"),
                        trade.mantomanCnt.as("mantomanCnt"),
                        trade.neatCnt.as("neatCnt"),
                        trade.blueJeansCnt.as("blueJeansCnt"),
                        trade.jeansCnt.as("jeansCnt"),
                        trade.skirtCnt.as("skirtCnt"),
                        trade.dressCnt.as("dressCnt"),
                        trade.suitCnt.as("suitCnt")))
                .from(trade)
                .where(formattedDate.eq(String.valueOf(LocalDate.now().minusDays(1))))
                .groupBy(formattedDate)
                .fetchOne();
    }

}
