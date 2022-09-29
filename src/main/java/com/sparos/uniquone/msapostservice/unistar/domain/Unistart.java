package com.sparos.uniquone.msapostservice.unistar.domain;
import com.sparos.uniquone.msapostservice.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
public class Unistart extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(nullable = false, columnDefinition = "VARCHAR(5) DEFAULT '원스타'")
    private String level;

}
