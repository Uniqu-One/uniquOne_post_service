package com.sparos.uniquone.msapostservice.post.domain;

import com.sparos.uniquone.msapostservice.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "corn_id", nullable = false)
    private Long cornId;

    @Column(name = "post_category_id")
    private Long postCategoryId;

    @Column(nullable = false,columnDefinition = "MEDIUMTEXT")
    private String dsc;

    @Column(name = "post_type", nullable = false, columnDefinition = "VARCHAR(10)")
    private String postType;

    @Column(columnDefinition = "VARCHAR(10)")
    private String condition;

    private Long price;

    private String color;

    @Column(nullable = false, columnDefinition = "default '0'")
    private Boolean isOffer;
}
