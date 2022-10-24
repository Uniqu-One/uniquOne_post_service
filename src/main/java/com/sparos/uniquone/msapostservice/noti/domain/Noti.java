package com.sparos.uniquone.msapostservice.noti.domain;

import com.sparos.uniquone.msapostservice.comment.domain.Comment;
import com.sparos.uniquone.msapostservice.cool.domain.Cool;
import com.sparos.uniquone.msapostservice.follow.domain.Follow;
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

    @OneToOne
    @JoinColumn(name = "cool_id")
    private Cool cool;

    @OneToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @OneToOne
    @JoinColumn(name = "follow_id")
    private Follow follow;

    @OneToOne
    @JoinColumn(name = "qna_id")
    private QnA qna;

    @JoinColumn(name = "noti_type")
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
}
