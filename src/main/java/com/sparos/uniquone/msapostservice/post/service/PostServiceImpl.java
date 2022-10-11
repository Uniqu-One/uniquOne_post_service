package com.sparos.uniquone.msapostservice.post.service;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import com.sparos.uniquone.msapostservice.look.repository.ILookRepository;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.post.domain.PostAndLook;
import com.sparos.uniquone.msapostservice.post.domain.PostImg;
import com.sparos.uniquone.msapostservice.post.domain.PostTag;
import com.sparos.uniquone.msapostservice.post.dto.PostChatResponseDto;
import com.sparos.uniquone.msapostservice.post.dto.PostInputDto;
import com.sparos.uniquone.msapostservice.post.repository.*;
import com.sparos.uniquone.msapostservice.util.s3.AwsS3UploaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostServiceImpl implements IPostService {

    private final IPostRepository iPostRepository;
    private final IPostImgRepository iPostImgRepository;
    private final ICornRepository iCornRepository;

    private final IPostTagRepository iPostTagRepository;

    private final IPostCategoryRepository iPostCategoryRepository;

    private final IPostAndLookRepository iPostAndLookRepository;

    private final ILookRepository iLookRepository;

    private final AwsS3UploaderService awsS3UploaderService;

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
    public Object addPost(PostInputDto postInputDto, List<MultipartFile> multipartFileList, Long userId) throws IOException {
        Optional<Corn> corn = iCornRepository.findByUserId(userId);
        Post post = iPostRepository.save(Post.builder()
                .corn(corn.get())
                .dsc(postInputDto.getDsc())
                .postType(postInputDto.getPostType())
                .postCategory(iPostCategoryRepository.findByName(postInputDto.getPostCategoryName()).get())
                .conditions(postInputDto.getConditions())
                .color(postInputDto.getColor())
                .build());

        String[] postTagList = postInputDto.getPostTagLine().split("#");
        for (String postDsc : postTagList) {
            if(!postDsc.isEmpty()&&!postDsc.equals(" ")&&!post.equals(null))
            iPostTagRepository.save(PostTag.builder()
                    .post(post)
                    .dsc(postDsc)
                    .build());
        }

        String[] lookList = postInputDto.getLookLine().split(",");
        for (String lookName : lookList) {
            iPostAndLookRepository.save(PostAndLook.builder()
                    .post(post)
                    .look(iLookRepository.findByName(lookName).get())
                    .build());
        }

        Integer idx = 1;
        for (MultipartFile multipartFile : multipartFileList) {
            if(!multipartFile.isEmpty())
            iPostImgRepository.save(PostImg.builder()
                    .post(post)
                    .url(awsS3UploaderService.upload(multipartFile, "uniquoneimg", "img"))
                    .idx(idx)
                    .build());
            idx++;
        }

        return "등록 완료되었습니다.";
    }


}
