package com.sparos.uniquone.msapostservice.corn.service;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.corn.dto.*;
import com.sparos.uniquone.msapostservice.corn.repository.CornRepositoryCustom;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import com.sparos.uniquone.msapostservice.follow.repository.IFollowRepository;
import com.sparos.uniquone.msapostservice.util.generate.GenerateRandomConNick;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import com.sparos.uniquone.msapostservice.util.s3.AwsS3UploaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
    public Object AddCorn(CornCreateDto cornCreateDto, HttpServletRequest request, MultipartFile multipartFile) throws IOException {
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        Corn corn = modelMapper.map(cornCreateDto, Corn.class);
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
            }else {
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
    public CornInfoDto GetMyCornInfo(Long userId) {
        Optional<Corn> corn = iCornRepository.findByUserId(userId);
        ReviewStarPostEAInfoOutputDto reviewStarPostEAInfoOutputDto = cornRepositoryCustom.findByCornIdPostReview(corn.get().getId());
        return CornInfoDto.builder()
                .imgUrl(corn.get().getImgUrl())
                .title(corn.get().getTitle())
                .reviewStar(reviewStarPostEAInfoOutputDto.getReviewStar())
                .reviewEA(reviewStarPostEAInfoOutputDto.getReviewEA())
                .postEA(reviewStarPostEAInfoOutputDto.getPostEA())
                .followerEA(iFollowRepository.countByCorn(corn.get()).get())
                .followingEA(iFollowRepository.countByUserId(userId).get())
                .url(corn.get().getUrl())
                .dsc(corn.get().getDsc()).build();
    }

    @Override
    public CornInfoDto GetOtherCornInfo(Long cornId) {
        Corn corn = iCornRepository.findById(cornId).orElseThrow();
        ReviewStarPostEAInfoOutputDto reviewStarPostEAInfoOutputDto = cornRepositoryCustom.findByCornIdPostReview(cornId);
        return CornInfoDto.builder()
                .imgUrl(corn.getImgUrl())
                .title(corn.getTitle())
                .reviewStar(reviewStarPostEAInfoOutputDto.getReviewStar())
                .reviewEA(reviewStarPostEAInfoOutputDto.getReviewEA())
                .postEA(reviewStarPostEAInfoOutputDto.getPostEA())
                .followerEA(iFollowRepository.countByCorn(corn).get())
                .followingEA(iFollowRepository.countByUserId(corn.getUserId()).get())
                .url(corn.getUrl())
                .dsc(corn.getDsc()).build();
    }

    @Override
    public CornInfoDto GetCornInfo(Long userId, Long cornId) {
        Optional<Corn> corn = iCornRepository.findByUserId(userId);
        ReviewStarPostEAInfoOutputDto reviewStarPostEAInfoOutputDto = cornRepositoryCustom.findByCornIdPostReview(corn.get().getId());
        return CornInfoDto.builder()
                .imgUrl(corn.get().getImgUrl())
                .title(corn.get().getTitle())
                .reviewStar(reviewStarPostEAInfoOutputDto.getReviewStar())
                .reviewEA(reviewStarPostEAInfoOutputDto.getReviewEA())
                .postEA(reviewStarPostEAInfoOutputDto.getPostEA())
                .followerEA(iFollowRepository.countByCorn(corn.get()).get())
                .followingEA(iFollowRepository.countByUserId(userId).get())
                .url(corn.get().getUrl())
                .dsc(corn.get().getDsc())
                .isFollow(iFollowRepository.existsByUserIdAndCornId(userId, cornId)).build();
    }

    @Override
    public CornModifyDto GetCornModifyInfo(Long userId) {
        Optional<Corn> corn = iCornRepository.findByUserId(userId);
        return CornModifyDto.builder()
                .imgUrl(corn.get().getImgUrl())
                .title(corn.get().getTitle())
                .url(corn.get().getUrl())
                .dsc(corn.get().getDsc())
                .build();
    }

    @Override
    public Object PatchCornModifyInfo(CornModifyDto cornModifyDto, MultipartFile multipartFile, Long userId) throws IOException {
        if ( multipartFile != null && !multipartFile.isEmpty()) {
            Optional<Corn> corn = iCornRepository.findByUserId(userId);
            corn.get().modImgUrl(awsS3UploaderService.upload(multipartFile, "uniquoneimg", "img"));
            corn.get().modTitle(cornModifyDto.getTitle());
            corn.get().modDsc(cornModifyDto.getDsc());
            corn.get().modUrl(cornModifyDto.getUrl());
            Corn saveCorn = iCornRepository.save(corn.get());
        } else {
            Optional<Corn> corn = iCornRepository.findByUserId(userId);
            corn.get().modImgUrl(cornModifyDto.getImgUrl());
            corn.get().modTitle(cornModifyDto.getTitle());
            corn.get().modDsc(cornModifyDto.getDsc());
            corn.get().modUrl(cornModifyDto.getUrl());
            Corn saveCorn = iCornRepository.save(corn.get());
        }

        return "수정이 완료되었습니다.";
    }

    @Override
    public CornRandomNickNameDto generatedNickName() {
        return new CornRandomNickNameDto(generateRandomConNick.generate());
    }


}
