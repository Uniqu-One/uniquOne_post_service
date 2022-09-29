package com.sparos.uniquone.msapostservice.trade.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    private Integer toSellerScore;

    private Integer toBuyerScore;

    @CreatedDate
    @Column(name = "reg_date", updatable = false)
    private LocalDateTime regDate;
}
