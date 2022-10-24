package com.sparos.uniquone.msapostservice.follow.service;

public interface IFollowService {
    Object postFollowing (Long cornId, Long userId);
    Object deleteUnFollowing(Long cornId, Long userId);

    Object getFollow(Long userId);
    Object getFollower(Long cornId);
}
