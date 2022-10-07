package com.sparos.uniquone.msapostservice.trade.domain;

import com.sparos.uniquone.msapostservice.post.domain.Post;
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
    @OneToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    private Integer toSellerScore;

    private Integer toBuyerScore;

    @CreatedDate
    @Column(name = "reg_date", updatable = false)
    private LocalDateTime regDate;
}
