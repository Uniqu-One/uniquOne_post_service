package com.sparos.uniquone.msapostservice.noti.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparos.uniquone.msapostservice.comment.domain.Comment;
import com.sparos.uniquone.msapostservice.cool.domain.Cool;
import com.sparos.uniquone.msapostservice.follow.domain.Follow;
import com.sparos.uniquone.msapostservice.offer.domain.Offer;
import com.sparos.uniquone.msapostservice.qna.domain.QnA;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(value ={AuditingEntityListener.class})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Getter
@Entity
public class Noti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cool_id")
    private Cool cool;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_id")
    private Follow follow;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_id")
    private QnA qna;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20)")
    private NotiType notiType;

    @Column(nullable = false, columnDefinition = "VARCHAR(100)")
    private String dsc;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean isCheck;

    @CreatedDate
    @Column(name = "reg_date", updatable = false)
    private LocalDateTime regDate;

    @Builder
    public Noti(Long userId, Cool cool, Comment comment, Follow follow, QnA qna, NotiType notiType, String dsc, Boolean isCheck) {
        this.userId = userId;
        this.cool = cool;
        this.comment = comment;
        this.follow = follow;
        this.qna = qna;
        this.notiType = notiType;
        this.dsc = dsc;
        this.isCheck = isCheck;
    }

    public void setCool(Cool cool) {
        this.cool = cool;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public void setFollow(Follow follow) {
        this.follow = follow;
    }

    public void setQna(QnA qna) {
        this.qna = qna;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public void modCheck(Boolean check) {
        isCheck = check;
    }
}
