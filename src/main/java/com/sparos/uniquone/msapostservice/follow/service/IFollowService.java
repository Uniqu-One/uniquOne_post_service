package com.sparos.uniquone.msapostservice.follow.service;

public interface IFollowService {
    Object postFollowing (Long cornId, Long userId);
    Object deleteUnFollowing(Long cornId, Long userId);

    Object getFollowing(Long userId);
    Object getOtherFollowing(Long cornId);
    Object getFollower(Long userId);
    Object getOtherFollower(Long cornId, Long userId);
    Object getOtherFollower(Long cornId);
}
