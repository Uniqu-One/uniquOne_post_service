package com.sparos.uniquone.msapostservice.util.view.Service;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.post.domain.PostImg;
import com.sparos.uniquone.msapostservice.post.dto.PostChatResponseDto;
import com.sparos.uniquone.msapostservice.post.repository.IPostImgRepository;
import com.sparos.uniquone.msapostservice.post.repository.IPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ViewServiceImpl implements IViewService{

    private final IPostRepository iPostRepository;
    private final IPostImgRepository iPostImgRepository;
    private final ICornRepository iCornRepository;

    // todo 포스트 이미지 리스트 처리
    // 채팅 - 게시물 정보 요청
    @Override
    public PostChatResponseDto chatPostInfo(Long postId, Long otherUserId) {
        // todo 존재 여부 확인
        Post post = iPostRepository.findById(postId).get();
        List<PostImg> postImgList = iPostImgRepository.findByPostId(post.getId());
        Corn corn = iCornRepository.findByUserId(otherUserId).get();

        return PostChatResponseDto.builder()
                .postId(post.getId())
                .postDsc(post.getDsc())
                .postPrice(post.getPrice())
                .postType(post.getPostType())
                .isOffer(post.getIsOffer())
                .postImg(postImgList.get(1).getUrl())
                .cornImg(corn.getImgUrl())
                .build();
    }


    // todo 포스트 이미지 리스트 처리
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
