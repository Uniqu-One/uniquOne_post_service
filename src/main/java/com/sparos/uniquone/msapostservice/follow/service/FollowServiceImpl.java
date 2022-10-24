package com.sparos.uniquone.msapostservice.follow.service;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import com.sparos.uniquone.msapostservice.follow.domain.Follow;
import com.sparos.uniquone.msapostservice.follow.dto.FollowingInfoDto;
import com.sparos.uniquone.msapostservice.follow.repository.FollowRepositoryCustom;
import com.sparos.uniquone.msapostservice.follow.repository.IFollowRepository;
import com.sparos.uniquone.msapostservice.util.feign.service.IUserConnect;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements IFollowService{
    private final IFollowRepository iFollowRepository;
    private final FollowRepositoryCustom followRepositoryCustom;
    private final ICornRepository iCornRepository;

    private final IUserConnect iUserConnect;

    @Override
    public Object postFollowing(Long cornId, Long userId) {
        Corn corn = iCornRepository.findById(cornId).orElseThrow();
        iFollowRepository.save(Follow.builder().corn(corn).userId(userId).build());
        return "팔로우 되었습니다.";
    }

    @Override
    public Object deleteUnFollowing(Long cornId, Long userId) {
        iFollowRepository.deleteByUserIdAndCornId(userId,cornId);
        return "팔로우 취소되었습니다.";
    }

    @Override
    public Object getFollow(Long userId) {
        List<Follow> followingList = iFollowRepository.findByUserId(userId);

        followingList.stream().map(follow ->
                {
                    FollowingInfoDto followingInfoDto = followRepositoryCustom.findByUserIdFollowingInfo(follow.getUserId());
                    followingInfoDto.addUserName(iUserConnect.getUserInfo(follow.getUserId()));
                    return followingInfoDto;
                });
        return followingList;
    }

    @Override
    public Object getFollower(Long cornId) {
        List<Follow> followerList = iFollowRepository.findByCorn(cornId);
        return followerList;
    }
}
