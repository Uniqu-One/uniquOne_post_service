package com.sparos.uniquone.msapostservice.cool.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Cool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
