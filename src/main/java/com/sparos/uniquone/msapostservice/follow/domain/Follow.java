package com.sparos.uniquone.msapostservice.follow.domain;
import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "corn_id", nullable = false)
    private Corn corn;

    @CreatedDate
    @Column(name = "reg_date", updatable = false)
    private LocalDateTime regDate;

    @Builder
    public Follow(Long id, Long userId, Corn corn, LocalDateTime regDate) {
        this.id = id;
        this.userId = userId;
        this.corn = corn;
        this.regDate = regDate;
    }
}
