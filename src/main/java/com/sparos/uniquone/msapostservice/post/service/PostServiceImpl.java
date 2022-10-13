package com.sparos.uniquone.msapostservice.post.service;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import com.sparos.uniquone.msapostservice.corn.service.ICornService;
import com.sparos.uniquone.msapostservice.look.repository.ILookRepository;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.post.domain.PostAndLook;
import com.sparos.uniquone.msapostservice.post.domain.PostImg;
import com.sparos.uniquone.msapostservice.post.domain.PostTag;
import com.sparos.uniquone.msapostservice.post.dto.OtherProfilePostAllList;
import com.sparos.uniquone.msapostservice.post.dto.PostChatResponseDto;
import com.sparos.uniquone.msapostservice.post.dto.PostInputDto;
import com.sparos.uniquone.msapostservice.post.dto.PostListInfoDto;
import com.sparos.uniquone.msapostservice.post.repository.*;
import com.sparos.uniquone.msapostservice.util.s3.AwsS3UploaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostServiceImpl implements IPostService {

    private final ICornRepository iCornRepository;
    private final IPostRepository iPostRepository;
    private final IPostImgRepository iPostImgRepository;

    private final IPostTagRepository iPostTagRepository;

    private final IPostCategoryRepository iPostCategoryRepository;

    private final IPostAndLookRepository iPostAndLookRepository;

    private final ILookRepository iLookRepository;

    private final AwsS3UploaderService awsS3UploaderService;

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
            if (!postDsc.isEmpty() && !postDsc.equals(" ") && !post.equals(null))
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
            if (!multipartFile.isEmpty())
                iPostImgRepository.save(PostImg.builder()
                        .post(post)
                        .url(awsS3UploaderService.upload(multipartFile, "uniquoneimg", "img"))
                        .idx(idx)
                        .build());
            idx++;
        }

        return "등록 완료되었습니다.";
    }

    @Override
    public Object modifyPost(PostInputDto postInputDto, Long userId, Long postId) {
        Optional<Post> post = iPostRepository.findById(postId);
        Optional<Corn> corn = iCornRepository.findByUserId(userId);
        if (post.isPresent() && post.get().getCorn().equals(corn.get())) {

            post.get().modDsc(postInputDto.getDsc());
            post.get().modPostType(postInputDto.getPostType());
            post.get().modPostCategoryName(iPostCategoryRepository.findByName(postInputDto.getPostCategoryName()).get());
            post.get().modConditions(postInputDto.getConditions());
            post.get().modColor(postInputDto.getColor());
            iPostRepository.save(post.get());

            List<PostTag> postTagEntityList = iPostTagRepository.findByPostId(postId);

            iPostTagRepository.deleteAllInBatch(postTagEntityList);

            String[] postTagList = postInputDto.getPostTagLine().split("#");
            for (String postDsc : postTagList) {
                if (!postDsc.isEmpty() && !postDsc.equals(" ") && !post.equals(null))
                    iPostTagRepository.save(PostTag.builder()
                            .post(post.get())
                            .dsc(postDsc)
                            .build());
            }

            List<PostAndLook> postAndLookEntityList = iPostAndLookRepository.findByPostId(postId);

            iPostAndLookRepository.deleteAllInBatch(postAndLookEntityList);

            String[] lookList = postInputDto.getLookLine().split(",");
            for (String lookName : lookList) {
                iPostAndLookRepository.save(PostAndLook.builder()
                        .post(post.get())
                        .look(iLookRepository.findByName(lookName).get())
                        .build());
            }

            return "수정이 완료되었습니다.";

        } else {

            return "존재하지 않는 게시물이거나 수정 권한이 없습니다";
        }
    }

    @Override
    public Object delPost(Long postId, Long userId) {

        iPostAndLookRepository.deleteAllInBatch(iPostAndLookRepository.findByPostId(postId));

        iPostImgRepository.deleteAllInBatch(iPostImgRepository.findByPostId(postId));

        iPostTagRepository.deleteAllInBatch(iPostTagRepository.findByPostId(postId));

        iPostRepository.deleteById(postId);

        return "삭제완료 하였습니다.";
    }

    @Override
    public Object getOtherPostAllList(Long cornId, Long userId) {
        List<Post> postList = iPostRepository.findByCornIdOrderByRegDateDesc(cornId);
        log.info(postList);
        List<PostListInfoDto> postListInfoDtoList = new ArrayList<>();
        for (Post post : postList) {
            PostImg postImg = iPostImgRepository.findOneByPostIdAndIdx(post.getId(), 1);
            postListInfoDtoList.add(PostListInfoDto.builder().postId(post.getId())
                    .postImg(postImg.getUrl())
                    .postType(post.getPostType())
                    .reg_date(post.getRegDate())
                    .build());
        }
        return postListInfoDtoList;
    }

}
