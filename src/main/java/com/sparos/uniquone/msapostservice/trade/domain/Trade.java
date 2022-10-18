package com.sparos.uniquone.msapostservice.trade.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;


import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Getter
@Entity
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sell_user_id", nullable = false)
    private Long sellerId;

    @Column(name = "buy_user_id", nullable = false)
    private Long buyerId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean isReview;

    @CreatedDate
    @Column(name = "reg_date", updatable = false)
    private LocalDateTime regDate;

    @Builder
    public Trade(Long id, Long sellerId, Long buyerId, Post post, Boolean isReview, LocalDateTime regDate) {
        this.id = id;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.post = post;
        this.isReview = isReview;
        this.regDate = regDate;
    }

    public void setReview(Boolean review) {
        isReview = review;
    }
}
