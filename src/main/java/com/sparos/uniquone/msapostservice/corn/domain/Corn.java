package com.sparos.uniquone.msapostservice.corn.domain;

import com.sparos.uniquone.msapostservice.util.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
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

}
