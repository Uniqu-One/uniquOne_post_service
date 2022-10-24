package com.sparos.uniquone.msapostservice.follow.domain;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
<<<<<<< HEAD
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
=======
import lombok.*;
>>>>>>> c513be62ed30ef9743bd5cd90b3c91b63f551bac
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
<<<<<<< HEAD
@AllArgsConstructor
@NoArgsConstructor
=======
@NoArgsConstructor(access = AccessLevel.PROTECTED)
>>>>>>> c513be62ed30ef9743bd5cd90b3c91b63f551bac
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
