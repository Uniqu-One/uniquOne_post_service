package com.sparos.uniquone.msapostservice.noti.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sparos.uniquone.msapostservice.comment.domain.Comment;
import com.sparos.uniquone.msapostservice.cool.domain.Cool;
import com.sparos.uniquone.msapostservice.follow.domain.Follow;
import com.sparos.uniquone.msapostservice.offer.domain.Offer;
import com.sparos.uniquone.msapostservice.qna.domain.QnA;
import com.sparos.uniquone.msapostservice.util.feign.dto.Chat;
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

    @Column(name = "nickName", nullable = false)
    private String nickName;

    @Column(name = "user_corn_img",columnDefinition = "MEDIUMTEXT")
    private String userCornImg;

    @Column(name = "post_img",columnDefinition = "MEDIUMTEXT")
    private String postImg;

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

    public Noti(Long userId, String nickName, String userCornImg, String postImg, NotiType notiType, String dsc, Boolean isCheck) {
        this.userId = userId;
        this.nickName = nickName;
        this.userCornImg = userCornImg;
        this.postImg = postImg;
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

    public void setNickname(String nickName) {
        this.nickName = nickName;
    }

    public void setUserCornImg(String userCornImg) {
        this.userCornImg = userCornImg;
    }

    public void setPostImg(String postImg) {
        this.postImg = postImg;
    }

    public void modCheck(Boolean check) {
        isCheck = check;
    }
}
