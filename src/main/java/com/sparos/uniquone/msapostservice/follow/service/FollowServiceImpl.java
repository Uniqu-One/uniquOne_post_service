package com.sparos.uniquone.msapostservice.follow.service;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import com.sparos.uniquone.msapostservice.follow.domain.Follow;
import com.sparos.uniquone.msapostservice.follow.dto.FollowerInfoDto;
import com.sparos.uniquone.msapostservice.follow.dto.FollowingInfoDto;
import com.sparos.uniquone.msapostservice.follow.repository.FollowRepositoryCustom;
import com.sparos.uniquone.msapostservice.follow.repository.IFollowRepository;
import com.sparos.uniquone.msapostservice.noti.domain.NotiType;
import com.sparos.uniquone.msapostservice.noti.service.IEmitterService;
import com.sparos.uniquone.msapostservice.util.feign.service.IUserConnect;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements IFollowService {
    private final IFollowRepository iFollowRepository;
    private final FollowRepositoryCustom followRepositoryCustom;
    private final ICornRepository iCornRepository;

    private final IUserConnect iUserConnect;
    private final IEmitterService iEmitterService;

    @Override
    public Object postFollowing(Long cornId, Long userId) {
        Corn corn = iCornRepository.findById(cornId).orElseThrow();
        Follow follow = iFollowRepository.save(Follow.builder().corn(corn).userId(userId).build());

        iEmitterService.send(corn.getUserId(), follow, NotiType.FOLLOW);

        return "팔로우 되었습니다.";
    }

    @Override
    public Object deleteUnFollowing(Long cornId, Long userId) {
        iFollowRepository.deleteByUserIdAndCornId(userId, cornId);
        return "팔로우 취소되었습니다.";
    }


    @Override
    public Object getFollowing(Long userId) {

        List<Follow> followingList = iFollowRepository.findByUserId(userId);
        List<FollowingInfoDto> followingInfoDtoList = followingList.stream().map(follow ->
        {
            FollowingInfoDto followingInfoDto = followRepositoryCustom.findByCornIdFollowingInfo(follow.getCorn().getId());
            followingInfoDto.addUserName(iUserConnect.getUserNickName(follow.getCorn().getUserId()));
            return followingInfoDto;
        }).collect(Collectors.toList());
        return followingInfoDtoList;
    }

    @Override
    public Object getOtherFollowing(Long cornId) {
        List<Follow> followingList = iFollowRepository.findByUserId(iCornRepository.findById(cornId).get().getUserId());
        List<FollowingInfoDto> followingInfoDtoList = followingList.stream().map(follow ->
        {
            FollowingInfoDto followingInfoDto = followRepositoryCustom.findByCornIdFollowingInfo(follow.getCorn().getId());
            followingInfoDto.addUserName(iUserConnect.getUserNickName(follow.getCorn().getUserId()));
            return followingInfoDto;
        }).collect(Collectors.toList());
        return followingInfoDtoList;
    }

    @Override
    public Object getFollower(Long userId) {
        Corn userCorn = iCornRepository.findByUserId(userId).orElseThrow();
        List<Follow> followerList = iFollowRepository.findByCornId(userCorn.getId());
        List<FollowerInfoDto> followerInfoDtoList = followerList.stream().map(follow -> {
            Optional<Corn> corn = iCornRepository.findByUserId(follow.getUserId());
            if(corn.isPresent()){
             return FollowerInfoDto.builder().cornTitle(corn.get().getTitle())
                     .cornImgUrl(corn.get().getImgUrl())
                     .cornId(corn.get().getId())
                     .userName(iUserConnect.getUserNickName(follow.getUserId()))
                     .userId(follow.getUserId()).build();
            }else{
            return FollowerInfoDto.builder()
                    .userName(iUserConnect.getUserNickName(follow.getUserId()))
                    .userId(follow.getUserId()).build();
            }
        }).collect(Collectors.toList());
        return followerInfoDtoList;
    }

    @Override
    public Object getOtherFollower(Long cornId) {
        List<Follow> followerList = iFollowRepository.findByCornId(cornId);
        List<FollowerInfoDto> followerInfoDtoList = followerList.stream().map(follow -> {
            Optional<Corn> corn = iCornRepository.findByUserId(follow.getUserId());
            if(corn.isPresent()){
                return FollowerInfoDto.builder().cornTitle(corn.get().getTitle())
                        .cornImgUrl(corn.get().getImgUrl())
                        .cornId(corn.get().getId())
                        .userName(iUserConnect.getUserNickName(follow.getUserId()))
                        .userId(follow.getUserId()).build();
            }else{
                return FollowerInfoDto.builder()
                        .userName(iUserConnect.getUserNickName(follow.getUserId()))
                        .userId(follow.getUserId()).build();
            }
        }).collect(Collectors.toList());
        return followerInfoDtoList;
    }
}

