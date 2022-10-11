package com.sparos.uniquone.msapostservice.post.service;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.post.domain.PostImg;
import com.sparos.uniquone.msapostservice.post.dto.PostChatResponseDto;
import com.sparos.uniquone.msapostservice.post.dto.PostInputDto;
import com.sparos.uniquone.msapostservice.post.repository.IPostCategoryRepository;
import com.sparos.uniquone.msapostservice.post.repository.IPostImgRepository;
import com.sparos.uniquone.msapostservice.post.repository.IPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements IPostService {

    private final IPostRepository iPostRepository;
    private final IPostImgRepository iPostImgRepository;
    private final ICornRepository iCornRepository;

    private final IPostCategoryRepository iPostCategoryRepository;

    // TODO 포스트 이미지 리스트 처리
    // 채팅 - 게시물 정보 요청
    @Override
    public PostChatResponseDto chatPostInfo(Long postId, Long otherUserId) {
        Post post = iPostRepository.findById(postId).get();
        PostImg postImg = iPostImgRepository.findByPostId(post.getId());
        Corn corn = iCornRepository.findByUserId(otherUserId).get();

        return PostChatResponseDto.builder()
                .postId(post.getId())
                .postDsc(post.getDsc())
                .postPrice(post.getPrice())
                .postType(post.getPostType())
                .isOffer(post.getIsOffer())
                .postImg(postImg.getUrl())
                .cornImg(corn.getImgUrl())
                .build();
    }

    @Override
    public void AddPost(PostInputDto postInputDto, Long userId) {
        Optional<Corn> corn = iCornRepository.findByUserId(userId);
        Post post = Post.builder().dsc(postInputDto.getDsc())
                .postType(postInputDto.getPostType())
                .postCategory(iPostCategoryRepository.findByName(postInputDto.getPostCategoryName()).get()).build();
    }
}
