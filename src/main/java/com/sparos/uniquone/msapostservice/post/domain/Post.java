package com.sparos.uniquone.msapostservice.post.domain;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.util.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Formula;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Getter
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

    @Column(nullable = false, columnDefinition = "VARCHAR(10)")
    private String title;

    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String dsc;

    @Column(name = "post_type", nullable = false, columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private PostType postType;

    @Column(columnDefinition = "VARCHAR(10)")
    private String conditions;

    private Integer price;

    private ProductSize productSize;

    private String color;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean isOffer;

    //추천 정렬 용도로 작성했는데 일단은 고려중.
    @Formula("(select count(c.post_id) from cool c where c.post_id = id group by c.post_id)")
    @Basic(fetch = FetchType.LAZY)
    private Long recommended;

    @Builder
    public Post(Long id, Corn corn, PostCategory postCategory, String title, String dsc, PostType postType, String conditions, Integer price, ProductSize productSize, String color, Boolean isOffer, Long recommended) {
        this.id = id;
        this.corn = corn;
        this.postCategory = postCategory;
        this.title = title;
        this.dsc = dsc;
        this.postType = postType;
        this.conditions = conditions;
        this.price = price;
        this.productSize = productSize;
        this.color = color;
        this.isOffer = isOffer;
        this.recommended = recommended;
    }

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
