package com.sparos.uniquone.msapostservice.report.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
