package com.sparos.uniquone.msapostservice.trade.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.post.domain.PostCategory;
import com.sparos.uniquone.msapostservice.post.domain.PostType;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Formula;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(value ={AuditingEntityListener.class})
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_category_id")
    private PostCategory postCategory;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean isReview;

    @CreatedDate
    @Column(name = "reg_date", updatable = false)
    private LocalDateTime regDate;

    @Formula("(select count(1) from trade t where DATE_FORMAT(t.reg_date, '%Y-%m-%d') = DATE_FORMAT(SUBDATE(now(), 1), '%Y-%m-%d') and t.post_category_id = 1)")
    @Basic(fetch = FetchType.LAZY)
    private Long outerCnt;

    @Formula("(select count(1) from trade t where DATE_FORMAT(t.reg_date, '%Y-%m-%d') = DATE_FORMAT(SUBDATE(now(), 1), '%Y-%m-%d') and t.post_category_id = 2)")
    @Basic(fetch = FetchType.LAZY)
    private Long shirtCnt;

    @Formula("(select count(1) from trade t where DATE_FORMAT(t.reg_date, '%Y-%m-%d') = DATE_FORMAT(SUBDATE(now(), 1), '%Y-%m-%d') and t.post_category_id = 3)")
    @Basic(fetch = FetchType.LAZY)
    private Long tShirtCnt;

    @Formula("(select count(1) from trade t where DATE_FORMAT(t.reg_date, '%Y-%m-%d') = DATE_FORMAT(SUBDATE(now(), 1), '%Y-%m-%d') and t.post_category_id = 4)")
    @Basic(fetch = FetchType.LAZY)
    private Long mantomanCnt;

    @Formula("(select count(1) from trade t where DATE_FORMAT(t.reg_date, '%Y-%m-%d') = DATE_FORMAT(SUBDATE(now(), 1), '%Y-%m-%d') and t.post_category_id = 5)")
    @Basic(fetch = FetchType.LAZY)
    private Long neatCnt;

    @Formula("(select count(1) from trade t where DATE_FORMAT(t.reg_date, '%Y-%m-%d') = DATE_FORMAT(SUBDATE(now(), 1), '%Y-%m-%d') and t.post_category_id = 6)")
    @Basic(fetch = FetchType.LAZY)
    private Long blueJeansCnt;

    @Formula("(select count(1) from trade t where DATE_FORMAT(t.reg_date, '%Y-%m-%d') = DATE_FORMAT(SUBDATE(now(), 1), '%Y-%m-%d') and t.post_category_id = 7)")
    @Basic(fetch = FetchType.LAZY)
    private Long jeansCnt;

    @Formula("(select count(1) from trade t where DATE_FORMAT(t.reg_date, '%Y-%m-%d') = DATE_FORMAT(SUBDATE(now(), 1), '%Y-%m-%d') and t.post_category_id = 8)")
    @Basic(fetch = FetchType.LAZY)
    private Long skirtCnt;

    @Formula("(select count(1) from trade t where DATE_FORMAT(t.reg_date, '%Y-%m-%d') = DATE_FORMAT(SUBDATE(now(), 1), '%Y-%m-%d') and t.post_category_id = 9)")
    @Basic(fetch = FetchType.LAZY)
    private Long dressCnt;

    @Formula("(select count(1) from trade t where DATE_FORMAT(t.reg_date, '%Y-%m-%d') = DATE_FORMAT(SUBDATE(now(), 1), '%Y-%m-%d') and t.post_category_id = 10)")
    @Basic(fetch = FetchType.LAZY)
    private Long suitCnt;

    @Builder
    public Trade(Long id, Long sellerId, Long buyerId, Post post, Boolean isReview, LocalDateTime regDate) {
        this.id = id;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.post = post;
        this.isReview = isReview;
        this.regDate = regDate;
    }

    public void modIsReview(Boolean isReview) {
        this.isReview = isReview;
    }
}
