package com.sparos.uniquone.msapostservice.post.domain;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Getter
@Builder
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corn_id", nullable = false)
    private Corn corn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_category_id")
    private PostCategory postCategory;

    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String dsc;

    @Column(name = "post_type", nullable = false, columnDefinition = "VARCHAR(10)")
    @Enumerated(EnumType.STRING)
    private PostType postType;

    @Column(columnDefinition = "VARCHAR(10)")
    private String conditions;

    private Long price;

    private String color;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean isOffer;

    public void modDsc(String dsc){
        this.dsc = dsc;
    }

    public void modPostCategoryName(PostCategory postCategory){
        this.postCategory = postCategory;
    }

    public void modPostType(PostType postType){
        this.postType = postType;
    }

    public void modConditions(String conditions){
        this.conditions = conditions;
    }

    public void modColor(String color){
        this.color = color;
    }
}
