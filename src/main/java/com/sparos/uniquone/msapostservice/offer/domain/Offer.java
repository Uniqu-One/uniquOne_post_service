package com.sparos.uniquone.msapostservice.offer.domain;

import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
public class Offer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne()
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    private Long price;

    @Enumerated(EnumType.STRING)
    @Column(name = "offer_type", nullable = false, columnDefinition = "VARCHAR(5) default '대기'")
    private OfferType offerType;

    @LastModifiedDate
    @Column(name = "check_date")
    private LocalDateTime checkDate;

    @CreatedDate
    @Column(name = "reg_date", updatable = false)
    private LocalDateTime regDate;

    @Builder
    public Offer(Long userId, Post post, Long price, OfferType offerType) {
        this.userId = userId;
        this.post = post;
        this.price = price;
        this.offerType = offerType;
    }
}
