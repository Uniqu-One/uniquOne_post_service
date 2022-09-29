package com.sparos.uniquone.msapostservice.noti.domain;

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

    @Column(name = "cool_id")
    private Long coolId;

    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "follow_id")
    private Long followId;

    @Column(name = "qna_id")
    private Long qnaId;

    @Column(name = "noti_type_id")
    private Long notiTypeId;

    @Column(nullable = false, columnDefinition = "VARCHAR(100)")
    private String dsc;

    @Column(nullable = false, columnDefinition = "default '0'")
    private Boolean isCheck;

    @CreatedDate
    @Column(name = "reg_date", updatable = false)
    private LocalDateTime regDate;

}
