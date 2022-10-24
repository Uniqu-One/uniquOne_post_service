package com.sparos.uniquone.msapostservice.report.domain;

import com.sparos.uniquone.msapostservice.post.domain.Post;
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
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private ReportType reportType;

    @CreatedDate
    @Column(name = "reg_date", updatable = false)
    private LocalDateTime regDate;

    @Builder
    public Report(Long userId, Post post, ReportType reportType) {
        this.userId = userId;
        this.post = post;
        this.reportType = reportType;
    }
}
