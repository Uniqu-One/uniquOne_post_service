package com.sparos.uniquone.msapostservice.corn.domain;

import com.sparos.uniquone.msapostservice.util.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Corn extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(10)")
    private String title;

    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String dsc;

    @Column(name = "img_url",columnDefinition = "MEDIUMTEXT")
    private String imgUrl;

    private String url;

    public void modTitle(String title){
        this.title=title;
    }

    public void modDsc(String dsc){
        this.dsc=dsc;
    }
    public void modImgUrl(String imgUrl){
        this.imgUrl=imgUrl;
    }

    public void modUrl(String url){
        this.url=url;
    }

}
