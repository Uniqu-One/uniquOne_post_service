package com.sparos.uniquone.msapostservice.review.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.util.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false, columnDefinition = "DOUBLE")
    private Double star;

    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String dsc;

    @Builder
    public Review(Long userId, Post post, Double star, String dsc) {
        this.userId = userId;
        this.post = post;
        this.star = star;
        this.dsc = dsc;
    }
}
