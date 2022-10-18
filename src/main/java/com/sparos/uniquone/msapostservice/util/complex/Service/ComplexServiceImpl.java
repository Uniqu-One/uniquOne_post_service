package com.sparos.uniquone.msapostservice.util.complex.Service;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.post.domain.PostImg;
import com.sparos.uniquone.msapostservice.post.dto.PostChatResponseDto;
import com.sparos.uniquone.msapostservice.post.repository.IPostImgRepository;
import com.sparos.uniquone.msapostservice.post.repository.IPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log4j2
public class ComplexServiceImpl implements IComplexService {

    private final IPostRepository iPostRepository;
    private final IPostImgRepository iPostImgRepository;
    private final ICornRepository iCornRepository;

    // 채팅 - 게시물 정보 요청
    @Override
    public PostChatResponseDto chatPostInfo(Long postId, Long otherUserId) {
        // todo 존재 여부 확인
        Post post = iPostRepository.findById(postId).orElseThrow();
        PostImg postImg = iPostImgRepository.findOneByPostIdAndIdx(post.getId(), 1);
        Optional<Corn> corn = iCornRepository.findByUserId(otherUserId);
        String imageUrl = null;

        if (!corn.isPresent()){
            imageUrl = "https://uniquoneimg.s3.ap-northeast-2.amazonaws.com/img/KakaoTalk_20221014_140108315.png";
        } else {
            imageUrl = corn.get().getImgUrl();
        }

        PostChatResponseDto chatResponseDto = PostChatResponseDto.builder()
                .postId(post.getId())
                .postDsc(post.getDsc())
                .postPrice(post.getPrice())
                .postType(post.getPostType())
                .isOffer(post.getIsOffer())
                .postImg(postImg.getUrl())
                .cornImg(imageUrl)
                .build();
        return chatResponseDto;
    }

    // 채팅 - 콘에 해당하는 포스트 존재 확인 API
    @Override
    public Boolean chatExistPost(Long postId, Long userId) {

        Optional<Corn> corn = iCornRepository.findByUserId(userId);
        if (!corn.isPresent()){
             return false;
        }

        Optional<Post> post = iPostRepository.findByIdAndCornId(postId, corn.get().getId());
        if (!post.isPresent()){
            return false;
        }

        return true;
    }

}
