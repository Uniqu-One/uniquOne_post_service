package com.sparos.uniquone.msapostservice.comment.domain;

import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String comment;

    private Integer ref;

    private Integer level;

    @Column(name = "ref_order")
    private Integer refOrder;

    @Column(name = "answer_num")
    private Integer answerNum;

    private Long parentId;

}
