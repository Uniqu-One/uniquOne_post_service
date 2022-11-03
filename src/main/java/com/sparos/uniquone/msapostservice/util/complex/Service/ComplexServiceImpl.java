package com.sparos.uniquone.msapostservice.util.complex.Service;

import com.sparos.uniquone.msapostservice.cool.repository.ICoolRepository;
import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import com.sparos.uniquone.msapostservice.follow.domain.Follow;
import com.sparos.uniquone.msapostservice.follow.repository.IFollowRepository;
import com.sparos.uniquone.msapostservice.noti.service.IEmitterService;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.post.domain.PostImg;
import com.sparos.uniquone.msapostservice.post.dto.PostChatResponseDto;
import com.sparos.uniquone.msapostservice.post.dto.PostSlicePageDto;
import com.sparos.uniquone.msapostservice.post.repository.IPostImgRepository;
import com.sparos.uniquone.msapostservice.post.repository.IPostRepository;
import com.sparos.uniquone.msapostservice.util.complex.dto.ChatPushDto;
import com.sparos.uniquone.msapostservice.util.complex.dto.CornInfoDto;
import com.sparos.uniquone.msapostservice.util.complex.dto.MainContentsDto;
import com.sparos.uniquone.msapostservice.util.complex.repository.ComplexRepositoryCustom;
import com.sparos.uniquone.msapostservice.util.feign.service.IUserConnect;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Log4j2
public class ComplexServiceImpl implements IComplexService {

    private final IPostRepository iPostRepository;
    private final IPostImgRepository iPostImgRepository;
    private final ICornRepository iCornRepository;
    private final IFollowRepository iFollowRepository;
    private final ICoolRepository iCoolRepository;
    private final IEmitterService iEmitterService;
    private final ComplexRepositoryCustom complexRepositoryCustom;

    private final IUserConnect iUserConnect;

    // 채팅 - 게시물 정보 요청
    @Override
    public PostChatResponseDto chatPostInfo(Long postId, Long otherUserId) {
        // todo 존재 여부 확인
        Post post = iPostRepository.findById(postId).orElseThrow();
        PostImg postImg = iPostImgRepository.findOneByPostIdAndIdx(post.getId(), 1);
        Optional<Corn> corn = iCornRepository.findByUserId(otherUserId);
        String imageUrl = null;

        if (corn.isPresent()) {
            imageUrl = corn.get().getImgUrl();
        }

        return PostChatResponseDto.builder()
                .postId(post.getId())
                .postTitle(post.getTitle())
                .postDsc(post.getDsc())
                .postPrice(post.getPrice())
                .postType(post.getPostType())
                .isOffer(post.getIsOffer())
                .postImg(postImg.getUrl())
                .cornImg(imageUrl)
                .build();
    }

    // 채팅 - 콘에 해당하는 포스트 존재 확인 API
    @Override
    public Boolean chatExistPost(Long postId, Long userId) {
        Optional<Corn> corn = iCornRepository.findByUserId(userId);
        if (!corn.isPresent()) {
            return false;
        }

        Optional<Post> post = iPostRepository.findByIdAndCornId(postId, corn.get().getId());
        if (!post.isPresent()) {
            return false;
        }

        return true;
    }

    @Override
    public Object getMainFollowContent(Long userId, Pageable pageable) {
        List<Follow> followList = iFollowRepository.findByUserId(userId);
        List<Long> followCornIdList = followList.stream().map(follow -> follow.getCorn().getId()).collect(Collectors.toList());
        List<MainContentsDto> mainContentsDtoList = complexRepositoryCustom.findByCornIdMainFollowContentsList(followCornIdList,userId,pageable);
        Boolean isLast = !(mainContentsDtoList.size()>pageable.getPageSize());
        if (!isLast) mainContentsDtoList.remove(pageable.getPageSize());
        mainContentsDtoList.stream().map(MainContentsDto -> {
            MainContentsDto.addIsCool(iCoolRepository.existsByUserIdAndPostId(userId, MainContentsDto.getPostId()));
            MainContentsDto.addPostImgUrl(iPostImgRepository.findOneByPostIdAndIdx(MainContentsDto.getPostId(),1).getUrl());
            MainContentsDto.addUserNickName(iUserConnect.getNickName(MainContentsDto.getUserId()));
            return MainContentsDto;
        }).collect(Collectors.toList());
        return PostSlicePageDto.builder()
                .content(mainContentsDtoList)
                .pageNumber(pageable.getPageNumber())
                .pageFirst(pageable.getPageSize()==0)
                .pageLast(isLast)
                .build();
    }

    @Override
    public Object getMainRecommendContent(Long userId, Pageable pageable) {
        List<Long> styleIdList = complexRepositoryCustom.findByUserIdCoolStyle(userId);
        List<MainContentsDto>  mainContentsDtoList = complexRepositoryCustom.findByStyleIdListMainContentsList(styleIdList,userId,pageable);
        Boolean isLast = !(mainContentsDtoList.size()>pageable.getPageSize());
        if (!isLast) mainContentsDtoList.remove(pageable.getPageSize());
        mainContentsDtoList.stream().map(MainContentsDto -> {
            MainContentsDto.addIsCool(iCoolRepository.existsByUserIdAndPostId(userId, MainContentsDto.getPostId()));
            MainContentsDto.addPostImgUrl(iPostImgRepository.findOneByPostIdAndIdx(MainContentsDto.getPostId(),1).getUrl());
            MainContentsDto.addUserNickName(iUserConnect.getNickName(MainContentsDto.getUserId()));
            return MainContentsDto;
        }).collect(Collectors.toList());
        PostSlicePageDto postSlicePageDto = PostSlicePageDto.builder()
                .content(mainContentsDtoList)
                .pageNumber(pageable.getPageNumber())
                .pageFirst(pageable.getPageSize()==0)
                .pageLast(isLast)
                .build();
        return postSlicePageDto;
    }

    @Override
    public Object getPostDetailPageCronInfo(Long cornId) {
        CornInfoDto cornInfoDto = complexRepositoryCustom.findByCornIdCornInfo(cornId);
        cornInfoDto.addUserNickName(iUserConnect.getNickName(cornInfoDto.getUserId()));
        return cornInfoDto;
    }

    @Override
    public void chatPush(ChatPushDto chatPushDto) {
        iEmitterService.sendChatPush(chatPushDto.getReceiverId(), chatPushDto.getPostId(), chatPushDto.getChat());
    }
    
    @Override
    public Long getUserIdByCorn(Long postId) {
        Post post = iPostRepository.findById(postId).get();
        Corn corn = iCornRepository.findById(post.getCorn().getId()).get();
        return corn.getUserId();
    }
}
