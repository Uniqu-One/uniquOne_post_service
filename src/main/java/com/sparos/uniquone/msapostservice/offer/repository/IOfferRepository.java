package com.sparos.uniquone.msapostservice.offer.repository;

import com.sparos.uniquone.msapostservice.offer.domain.Offer;
import com.sparos.uniquone.msapostservice.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IOfferRepository extends JpaRepository<Offer, Long> {

/*    @Query("SELECT o.price, o.post.id, \n" +
            "count(case when o.offerType = 'WAITING' then 'WAITING' end) as waitingCut, \n" +
            "count(case when o.offerType = 'ACCEPT' then 'ACCEPT' end) as acceptCut, \n" +
            "count(case when o.offerType = 'REFUSE' then 'REFUSE' end) as refuseCut \n" +
            "FROM offer as o \n" +
            "where o.post.id in :postIds\n" +
            "group by o.post.id")
    List<Object> findByPostIdIn(@Param("postIds") List<Long> postIds);*/

    List<Offer> findByPostId(Long postId);
}
