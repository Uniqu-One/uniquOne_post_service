package com.sparos.uniquone.msapostservice.offer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.util.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Formula;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


@EntityListeners(value ={AuditingEntityListener.class})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Getter
@Entity
public class Offer{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    private Long price;

    @Enumerated(EnumType.STRING)
    @Column(name = "offer_type", nullable = false, columnDefinition = "VARCHAR(20) default 'WAITING'")
    private OfferType offerType;

    @LastModifiedDate
    @Column(name = "check_date")
    private LocalDateTime checkDate;

    @CreatedDate
    @Column(name = "reg_date", updatable = false)
    private LocalDateTime regDate;

    @Formula("(select count(1) from offer o where o.post_id = post_id and o.offer_type = \"WAITING\")")
    @Basic(fetch = FetchType.LAZY)
    private Long waitingCnt;

    @Formula("(select count(1) from offer o where o.post_id = post_id and o.offer_type = \"ACCEPT\")")
    @Basic(fetch = FetchType.LAZY)
    private Long acceptCount;

    @Formula("(select count(1) from offer o where o.post_id = post_id and o.offer_type = \"REFUSE\")")
    @Basic(fetch = FetchType.LAZY)
    private Long refuseCount;

    @Builder
    public Offer(Long userId, Post post, Long price, OfferType offerType) {
        this.userId = userId;
        this.post = post;
        this.price = price;
        this.offerType = offerType;
    }

    public void modOfferType(OfferType offerType) {
        this.offerType = offerType;
    }
}
