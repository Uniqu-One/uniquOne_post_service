package com.sparos.uniquone.msapostservice.corn.service;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.corn.dto.*;
import com.sparos.uniquone.msapostservice.corn.repository.CornRepositoryCustom;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import com.sparos.uniquone.msapostservice.follow.domain.Follow;
import com.sparos.uniquone.msapostservice.follow.repository.IFollowRepository;
import com.sparos.uniquone.msapostservice.util.generate.GenerateRandomConNick;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import com.sparos.uniquone.msapostservice.util.response.ExceptionCode;
import com.sparos.uniquone.msapostservice.util.response.UniquOneServiceException;
import com.sparos.uniquone.msapostservice.util.s3.AwsS3UploaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CornServiceImpl implements ICornService {
    private final ModelMapper modelMapper;
    private final ICornRepository iCornRepository;
    private final CornRepositoryCustom cornRepositoryCustom;
    private final AwsS3UploaderService awsS3UploaderService;
    private final IFollowRepository iFollowRepository;
    private final GenerateRandomConNick generateRandomConNick;

    @Override
    public Object addCorn(CornCreateDto cornCreateDto, HttpServletRequest request, MultipartFile multipartFile) throws IOException {
        Long userPkId = JwtProvider.getUserPkId(request);
        String userNickName = JwtProvider.getUserNickName(request);
        if (!iCornRepository.existsByUserId(userPkId)) {
            if (!multipartFile.isEmpty()) {
                Corn corn = Corn.builder().userId(userPkId)
                        .userNickName(userNickName)
                        .title(cornCreateDto.getTitle())
                        .dsc(cornCreateDto.getDsc())
                        .imgUrl(awsS3UploaderService.upload(multipartFile, "uniquoneimg", "img"))
                        .build();
                iCornRepository.save(corn);
            } else {
                Corn corn = Corn.builder().userId(userPkId)
                        .userNickName(userNickName)
                        .title(cornCreateDto.getTitle())
                        .dsc(cornCreateDto.getDsc())
                        .build();
                iCornRepository.save(corn);
            }
            return "개설되었습니다";
        } else {
            return "이미 생성된 유저";
        }
    }

    @Override
    public CornInfoDto getMyCornInfo(Long userId) {
        Corn corn = iCornRepository.findByUserId(userId).orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH_CORN_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED));
        ReviewStarPostEAInfoOutputDto reviewStarPostEAInfoOutputDto = cornRepositoryCustom.findByCornIdPostReview(corn.getId());
        if (reviewStarPostEAInfoOutputDto != null) {
            return CornInfoDto.builder()
                    .imgUrl(corn.getImgUrl())
                    .title(corn.getTitle())
                    .reviewStar(reviewStarPostEAInfoOutputDto.getReviewStar())
                    .reviewEA(reviewStarPostEAInfoOutputDto.getReviewEA())
                    .postEA(reviewStarPostEAInfoOutputDto.getPostEA())
                    .followerEA(iFollowRepository.countByCorn(corn).get())
                    .followingEA(iFollowRepository.countByUserId(userId).get())
                    .url(corn.getUrl())
                    .dsc(corn.getDsc())
                    .userNickName(corn.getUserNickName())
                    .build();
        } else {
            return CornInfoDto.builder()
                    .imgUrl(corn.getImgUrl())
                    .title(corn.getTitle())
                    .followerEA(iFollowRepository.countByCorn(corn).get())
                    .followingEA(iFollowRepository.countByUserId(userId).get())
                    .url(corn.getUrl())
                    .dsc(corn.getDsc())
                    .userNickName(corn.getUserNickName())
                    .build();
        }

    }

    @Override
    public CornInfoDto userGetOtherCornInfo(Long cornId, Long userId) {
        Corn corn = iCornRepository.findById(cornId).orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH_CORN_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED));
        List<Follow> follower = iFollowRepository.findByCornId(cornId);
        ReviewStarPostEAInfoOutputDto reviewStarPostEAInfoOutputDto = cornRepositoryCustom.findByCornIdPostReview(cornId);
        if (reviewStarPostEAInfoOutputDto != null) {
            return CornInfoDto.builder()
                    .imgUrl(corn.getImgUrl())
                    .title(corn.getTitle())
                    .reviewStar(reviewStarPostEAInfoOutputDto.getReviewStar())
                    .reviewEA(reviewStarPostEAInfoOutputDto.getReviewEA())
                    .postEA(reviewStarPostEAInfoOutputDto.getPostEA())
                    .followerEA(follower.stream().count())
                    .followingEA(iFollowRepository.countByUserId(corn.getUserId()).get())
                    .url(corn.getUrl())
                    .dsc(corn.getDsc())
                    .userNickName(corn.getUserNickName())
                    .isFollow(follower.stream().anyMatch(follow -> follow.getUserId().equals(userId))).build();
        } else {
            return CornInfoDto.builder()
                    .imgUrl(corn.getImgUrl())
                    .title(corn.getTitle())
                    .followerEA(follower.stream().count())
                    .followingEA(iFollowRepository.countByUserId(corn.getUserId()).get())
                    .url(corn.getUrl())
                    .dsc(corn.getDsc())
                    .userNickName(corn.getUserNickName())
                    .isFollow(follower.stream().anyMatch(follow -> follow.getUserId().equals(userId))).build();
        }
    }

    @Override
    public CornInfoDto getOtherCornInfo(Long cornId) {
        Corn corn = iCornRepository.findById(cornId).orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH_CORN_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED));
        List<Follow> follower = iFollowRepository.findByCornId(cornId);
        ReviewStarPostEAInfoOutputDto reviewStarPostEAInfoOutputDto = cornRepositoryCustom.findByCornIdPostReview(cornId);
        if (reviewStarPostEAInfoOutputDto != null) {
            return CornInfoDto.builder()
                    .imgUrl(corn.getImgUrl())
                    .title(corn.getTitle())
                    .reviewStar(reviewStarPostEAInfoOutputDto.getReviewStar())
                    .reviewEA(reviewStarPostEAInfoOutputDto.getReviewEA())
                    .postEA(reviewStarPostEAInfoOutputDto.getPostEA())
                    .followerEA(follower.stream().count())
                    .followingEA(iFollowRepository.countByUserId(corn.getUserId()).get())
                    .url(corn.getUrl())
                    .dsc(corn.getDsc())
                    .userNickName(corn.getUserNickName())
                    .build();
        } else {
            return CornInfoDto.builder()
                    .imgUrl(corn.getImgUrl())
                    .title(corn.getTitle())
                    .followerEA(follower.stream().count())
                    .followingEA(iFollowRepository.countByUserId(corn.getUserId()).get())
                    .url(corn.getUrl())
                    .dsc(corn.getDsc())
                    .userNickName(corn.getUserNickName())
                    .build();
        }
    }

    @Override
    public CornInfoDto getCornInfo(Long userId, Long cornId) {
        Corn corn = iCornRepository.findByUserId(userId).orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH_CORN_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED));
        ReviewStarPostEAInfoOutputDto reviewStarPostEAInfoOutputDto = cornRepositoryCustom.findByCornIdPostReview(corn.getId());
        if (reviewStarPostEAInfoOutputDto != null) {
            return CornInfoDto.builder()
                    .imgUrl(corn.getImgUrl())
                    .title(corn.getTitle())
                    .reviewStar(reviewStarPostEAInfoOutputDto.getReviewStar())
                    .reviewEA(reviewStarPostEAInfoOutputDto.getReviewEA())
                    .postEA(reviewStarPostEAInfoOutputDto.getPostEA())
                    .followerEA(iFollowRepository.countByCorn(corn).get())
                    .followingEA(iFollowRepository.countByUserId(userId).get())
                    .url(corn.getUrl())
                    .dsc(corn.getDsc())
                    .userNickName(corn.getUserNickName())
                    .isFollow(iFollowRepository.existsByUserIdAndCornId(userId, cornId)).build();
        } else {
            return CornInfoDto.builder()
                    .imgUrl(corn.getImgUrl())
                    .title(corn.getTitle())
                    .followerEA(iFollowRepository.countByCorn(corn).get())
                    .followingEA(iFollowRepository.countByUserId(userId).get())
                    .url(corn.getUrl())
                    .dsc(corn.getDsc())
                    .userNickName(corn.getUserNickName())
                    .isFollow(iFollowRepository.existsByUserIdAndCornId(userId, cornId)).build();
        }
    }

    @Override
    public CornModifyDto getCornModifyInfo(Long userId) {
        Corn corn = iCornRepository.findByUserId(userId).orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH_CORN_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED));
        return CornModifyDto.builder()
                .imgUrl(corn.getImgUrl())
                .title(corn.getTitle())
                .url(corn.getUrl())
                .dsc(corn.getDsc())
                .build();
    }

    @Override
    public Object patchCornModifyInfo(CornModifyDto cornModifyDto, MultipartFile multipartFile, Long userId) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            Corn corn = iCornRepository.findByUserId(userId).orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH_CORN_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED));
            corn.modImgUrl(awsS3UploaderService.upload(multipartFile, "uniquoneimg", "img"));
            corn.modTitle(cornModifyDto.getTitle());
            corn.modDsc(cornModifyDto.getDsc());
            corn.modUrl(cornModifyDto.getUrl());
            Corn saveCorn = iCornRepository.save(corn);
        } else {
            Corn corn = iCornRepository.findByUserId(userId).orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH_CORN_ELEMENT_EXCEPTION, HttpStatus.ACCEPTED));
            corn.modImgUrl(cornModifyDto.getImgUrl());
            corn.modTitle(cornModifyDto.getTitle());
            corn.modDsc(cornModifyDto.getDsc());
            corn.modUrl(cornModifyDto.getUrl());
            Corn saveCorn = iCornRepository.save(corn);
        }

        return "수정이 완료되었습니다.";
    }

    @Override
    public CornRandomNickNameDto generatedNickName() {
        return new CornRandomNickNameDto(generateRandomConNick.generate());
    }

    @Override
    public Object isCornExistence(Long userId) {
        Map<String, Boolean> isMap = new HashMap<>();
        isMap.put("isCornExistence", iCornRepository.existsByUserId(userId));
        return isMap;
    }


}
