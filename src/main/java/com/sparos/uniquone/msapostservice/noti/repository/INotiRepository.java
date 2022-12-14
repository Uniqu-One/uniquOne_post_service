package com.sparos.uniquone.msapostservice.noti.repository;

import com.sparos.uniquone.msapostservice.noti.domain.Noti;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface INotiRepository extends JpaRepository<Noti, Long> {

    List<Noti> findByUserId(Long userId, Pageable pageable);

    Optional<Noti> findByIdAndUserId(Long notiId, Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE Noti SET cool_id = null WHERE cool_id =:coolId")
    void updateCoolByCoolId(@Param("coolId") Long coolId);

    @Transactional
    @Modifying
    @Query("UPDATE Noti SET follow_id = null WHERE follow_id =:followId")
    void updateFollowByFollowId(@Param("followId") Long followId);

    @Transactional
    @Modifying
    @Query("UPDATE Noti SET comment_id = null WHERE comment_id =:commentId")
    void updateCommentByCommentId(@Param("commentId") Long commentId);


    List<Noti> findByUserId(Long userId);

    Long countByUserIdAndIsCheck(Long userId, Boolean isCheck);

    @Transactional
    @Modifying
    @Query("UPDATE Noti SET isCheck = true WHERE userId =:userId AND isCheck = false")
    void updateIsCheckByUserId(@Param("userId") Long userId);
}
