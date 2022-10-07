package com.sparos.uniquone.msapostservice.noti.domain;

import com.sparos.uniquone.msapostservice.comment.domain.Comment;
import com.sparos.uniquone.msapostservice.cool.domain.Cool;
import com.sparos.uniquone.msapostservice.follow.domain.Follow;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
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
    @Column(name = "qna_id")
    private Long qnaId;
    @ManyToOne
    @JoinColumn(name = "noti_type_id")
    private NotiType notiType;

    @Column(nullable = false, columnDefinition = "VARCHAR(100)")
    private String dsc;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean isCheck;

    @CreatedDate
    @Column(name = "reg_date", updatable = false)
    private LocalDateTime regDate;

}
