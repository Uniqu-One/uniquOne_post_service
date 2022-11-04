
package com.sparos.uniquone.msapostservice.unistar.repository;

import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.unistar.domain.UniStar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUniStarRepository extends JpaRepository<UniStar, Long> {
    boolean existsByPostAndUserId(Post post, Long userPkId);

    Optional<UniStar> findByPostAndUserId(Post post, Long userPkId);

    UniStar findByUserIdAndPost(Long userPkId,Post post);
}
