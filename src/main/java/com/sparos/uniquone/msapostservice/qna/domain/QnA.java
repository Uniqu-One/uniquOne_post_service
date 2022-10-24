package com.sparos.uniquone.msapostservice.qna.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Getter
@Entity
public class QnA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;


    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String question;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(10)")
    private QuestionType questionType;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String answer;

    @CreatedDate
    @Column(name = "q_reg_date", updatable = false)
    private LocalDateTime qRegDate;

    @LastModifiedDate
    @Column(name = "a_reg_date")
    private LocalDateTime aRegDate;

    @Builder
    public QnA(Long userId, String question, QuestionType questionType, String answer) {
        this.userId = userId;
        this.question = question;
        this.questionType = questionType;
        this.answer = answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
