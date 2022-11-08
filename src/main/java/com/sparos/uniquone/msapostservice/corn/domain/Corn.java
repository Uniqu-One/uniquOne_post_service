package com.sparos.uniquone.msapostservice.corn.domain;

import com.sparos.uniquone.msapostservice.util.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Corn extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    private String userNickName;

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

    @Builder
    public Corn (Long userId,String userNickName ,String title, String dsc, String imgUrl, String url) {
        this.userId = userId;
        this.userNickName = userNickName;
        this.title = title;
        this.dsc = dsc;
        this.imgUrl = imgUrl;
        this.url = url;
    }

}
