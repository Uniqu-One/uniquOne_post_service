package com.sparos.uniquone.msapostservice.follow.domain;
import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value ={AuditingEntityListener.class})
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corn_id", nullable = false)
    private Corn corn;

    @CreatedDate
    @Column(name = "reg_date", updatable = false)
    private LocalDateTime regDate;

    @Builder
    public Follow(Long userId, Corn corn, LocalDateTime regDate) {
        this.userId = userId;
        this.corn = corn;
        this.regDate = regDate;
    }
}
