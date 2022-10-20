package com.sparos.uniquone.msapostservice.post.service;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import com.sparos.uniquone.msapostservice.look.repository.ILookRepository;
import com.sparos.uniquone.msapostservice.post.domain.*;
import com.sparos.uniquone.msapostservice.post.dto.PostInputDto;
import com.sparos.uniquone.msapostservice.post.dto.PostListInfoDto;
import com.sparos.uniquone.msapostservice.post.dto.PostModInfoDto;
import com.sparos.uniquone.msapostservice.post.repository.*;
import com.sparos.uniquone.msapostservice.util.s3.AwsS3UploaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sparos.uniquone.msapostservice.post.domain.PostType.*;

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
    private final PostRepositoryCustom postRepositoryCustom;

    @Override
    public Object addPost(PostInputDto postInputDto, List<MultipartFile> multipartFileList, Long userId) throws IOException {
        Optional<Corn> corn = iCornRepository.findByUserId(userId);
        Post post = iPostRepository.save(Post.builder()
                        .title("dkdkdk")
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
        log.info(SALE.ordinal());
        List<Post> postList = iPostRepository.findByCornIdOrderByRegDateDesc(cornId);
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

    @Override
    public Object getOtherPostProductList(Long cornId, Long userId, Pageable pageable) {
        List<PostType> postTypeList = new ArrayList<>();
        postTypeList.add(SALE);
        postTypeList.add(DISCONTINUED);
        postTypeList.add(SOLD_OUT);
        return postRepositoryCustom.PostProductListInfo(postTypeList, cornId, pageable);
//        List<Post> SalePostList = iPostRepository.findByCornIdAndPostTypeOrderByRegDateDesc(cornId, SALE);
//        List<Post> SoldOutpostList = iPostRepository.findByCornIdAndPostTypeOrderByRegDateDesc(cornId, SOLD_OUT);
//        List<Post> DiscontinuedPostList = iPostRepository.findByCornIdAndPostTypeOrderByRegDateDesc(cornId, DISCONTINUED);
//        List<PostListInfoDto> postSaleListInfoDtoList = new ArrayList<>();
//        List<PostListInfoDto> postSoldOutListInfoDtoList = new ArrayList<>();
//        List<PostListInfoDto> postDiscontinuedListInfoDtoList = new ArrayList<>();
//        for (Post post : SalePostList) {
//            PostImg postImg = iPostImgRepository.findOneByPostIdAndIdx(post.getId(), 1);
//            postSaleListInfoDtoList.add(PostListInfoDto.builder().postId(post.getId())
//                    .postImg(postImg.getUrl())
//                    .postType(post.getPostType())
//                    .reg_date(post.getRegDate())
//                    .build());
//        }
//        for (Post post : SoldOutpostList) {
//            PostImg postImg = iPostImgRepository.findOneByPostIdAndIdx(post.getId(), 1);
//            postSoldOutListInfoDtoList.add(PostListInfoDto.builder().postId(post.getId())
//                    .postImg(postImg.getUrl())
//                    .postType(post.getPostType())
//                    .reg_date(post.getRegDate())
//                    .build());
//        }
//        for (Post post : DiscontinuedPostList) {
//            PostImg postImg = iPostImgRepository.findOneByPostIdAndIdx(post.getId(), 1);
//            postDiscontinuedListInfoDtoList.add(PostListInfoDto.builder().postId(post.getId())
//                    .postImg(postImg.getUrl())
//                    .postType(post.getPostType())
//                    .reg_date(post.getRegDate())
//                    .build());
//        }
//        return OtherProfilePostProductList.builder().postDiscontinuedListInfoDtoList(postDiscontinuedListInfoDtoList)
//                .postSaleListInfoDtoList(postSaleListInfoDtoList)
//                .postSoldOutListInfoDtoList(postSoldOutListInfoDtoList).build();
    }

    @Override
    public Object getOtherPostStyleList(Long userId, Long cornId) {
        List<Post> PostList = iPostRepository.findByCornIdAndPostTypeOrderByRegDateDesc(cornId, STYLE);
        List<PostListInfoDto> postListInfoDtoList = new ArrayList<>();
        for (Post post : PostList) {
            PostImg postImg = iPostImgRepository.findOneByPostIdAndIdx(post.getId(), 1);
            postListInfoDtoList.add(PostListInfoDto.builder().postId(post.getId())
                    .postImg(postImg.getUrl())
                    .postType(post.getPostType())
                    .reg_date(post.getRegDate())
                    .build());
        }
        return postListInfoDtoList;
    }

    @Override
    public Object getModPostInfo(Long userId, Long postId) {
        Post post = iPostRepository.findById(postId).orElseThrow();
        List<String> colorList = List.of(post.getColor().split(","));
        List<PostTag> postTagList = iPostTagRepository.findByPostId(postId);
        List<String> postTagNameList = postTagList.stream().map(postTag -> "#"+postTag.getDsc()).collect(Collectors.toList());
        List<PostAndLook> postAndLookList = iPostAndLookRepository.findByPostId(postId);
        List<String> postAndLookNameList = postAndLookList.stream().map(postAndLook -> postAndLook.getLook().getName()).collect(Collectors.toList());
        List<PostImg> postImgList = iPostImgRepository.findByPostId(postId);
        List<String> postImgUrlList = postImgList.stream().map(postImg -> postImg.getUrl()).collect(Collectors.toList());
        PostModInfoDto postModInfoDto = PostModInfoDto.builder()
                .postImgList(postImgUrlList)
                .dsc(post.getDsc())
                .postTagNameList(postTagNameList)
                .postType(post.getPostType())
                .postCategoryName(post.getPostCategory().getName())
                .conditions(post.getConditions())
                .lookList(postAndLookNameList)
                .color(colorList)
                .build();
        return postModInfoDto;
    }

}
