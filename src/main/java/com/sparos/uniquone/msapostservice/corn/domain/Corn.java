package com.sparos.uniquone.msapostservice.corn.domain;

import com.sparos.uniquone.msapostservice.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
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

    private String url;

}
