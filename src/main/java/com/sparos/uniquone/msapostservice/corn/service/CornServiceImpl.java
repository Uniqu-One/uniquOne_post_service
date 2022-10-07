package com.sparos.uniquone.msapostservice.corn.service;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.corn.dto.CornCreateDto;
import com.sparos.uniquone.msapostservice.corn.dto.CornInfoDto;
import com.sparos.uniquone.msapostservice.corn.dto.CornUserInfoDto;
import com.sparos.uniquone.msapostservice.corn.dto.ReviewStarPostEAInfoOutputDto;
import com.sparos.uniquone.msapostservice.corn.repository.CornRepositoryCustom;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import com.sparos.uniquone.msapostservice.follow.dto.IsFollowDto;
import com.sparos.uniquone.msapostservice.follow.repository.IFollowRepository;
import com.sparos.uniquone.msapostservice.util.s3.AwsS3UploaderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CornServiceImpl implements ICornService {
    private final ModelMapper modelMapper;
    private final ICornRepository iCornRepository;
    private final CornRepositoryCustom cornRepositoryCustom;
    private final AwsS3UploaderService awsS3UploaderService;
    private final IFollowRepository iFollowRepository;

    @Override
    public void AddCorn(CornCreateDto cornCreateDto, MultipartFile multipartFile) throws IOException {
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        Corn corn = modelMapper.map(cornCreateDto, Corn.class);

        Corn corn = Corn.builder().userId(cornCreateDto.getUserId())
                .title(cornCreateDto.getTitle())
                .dsc(cornCreateDto.getDsc())
                .imgUrl(awsS3UploaderService.upload(multipartFile, "uniquoneimg", "img"))
                .build();


        iCornRepository.save(corn);
    }

    @Override
    public CornInfoDto GetCornMyInfo(Long userId) {
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

    public CornUserInfoDto GetCornUserInfo(Long userId, Long cornId) {
        Optional<Corn> corn = iCornRepository.findByUserId(userId);
        ReviewStarPostEAInfoOutputDto reviewStarPostEAInfoOutputDto = cornRepositoryCustom.findByCornIdPostReview(corn.get().getId());
        CornInfoDto cornInfoDto = CornInfoDto.builder()
                .imgUrl(corn.get().getImgUrl())
                .title(corn.get().getTitle())
                .reviewStar(reviewStarPostEAInfoOutputDto.getReviewStar())
                .reviewEA(reviewStarPostEAInfoOutputDto.getReviewEA())
                .postEA(reviewStarPostEAInfoOutputDto.getPostEA())
                .followerEA(iFollowRepository.countByCorn(corn.get()).get())
                .followingEA(iFollowRepository.countByUserId(userId).get())
                .url(corn.get().getUrl())
                .dsc(corn.get().getDsc()).build();
        IsFollowDto isFollowDto = IsFollowDto.builder().isFollow(iFollowRepository.existsByUserIdAndCornId(userId, cornId)).build();
        return CornUserInfoDto.builder().cornInfoDto(cornInfoDto).isFollowDto(isFollowDto).build();
    }

}
