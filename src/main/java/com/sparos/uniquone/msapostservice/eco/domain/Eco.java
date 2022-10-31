package com.sparos.uniquone.msapostservice.eco.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@EntityListeners(value ={AuditingEntityListener.class})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Getter
@Entity
public class Eco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double water;

    @Column(nullable = false)
    private Double carbon;

    @Column(nullable = false)
    private Double distance;

    @Column(name = "reg_date")
    private String regDate;

    @Builder
    public Eco(Double water, Double carbon, Double distance, String regDate) {
        this.water = water;
        this.carbon = carbon;
        this.distance = distance;
        this.regDate = regDate;
    }

}
