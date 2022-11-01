package com.sparos.uniquone.msapostservice.post.service;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import com.sparos.uniquone.msapostservice.look.repository.ILookRepository;
import com.sparos.uniquone.msapostservice.post.domain.*;
import com.sparos.uniquone.msapostservice.post.dto.*;
import com.sparos.uniquone.msapostservice.post.repository.*;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import com.sparos.uniquone.msapostservice.util.response.ExceptionCode;
import com.sparos.uniquone.msapostservice.util.response.UniquOneServiceException;
import com.sparos.uniquone.msapostservice.util.s3.AwsS3UploaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
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
                .title(postInputDto.getTitle())
                .corn(corn.get())
                .dsc(postInputDto.getDsc())
                .postType(postInputDto.getPostType())
                .postCategory(iPostCategoryRepository.findByName(postInputDto.getPostCategoryName()).get())
                .conditions(postInputDto.getConditions())
                .color(postInputDto.getColor())
                .price(postInputDto.getPrice())
                .productSize(postInputDto.getProductSize())
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
    public Object getMyPostAllList(Long userId, Pageable pageable) {
        Long cornId = iCornRepository.findByUserId(userId).orElseThrow().getId();
        Slice<Post> postList = iPostRepository.findByCornIdOrderByRegDateDesc(cornId, pageable);
        List<PostListInfoDto> postListInfoDtoList = postList.stream().map(post ->
                PostListInfoDto.builder()
                        .postId(post.getId())
                        .postImg(iPostImgRepository.findOneByPostIdAndIdx(post.getId(), 1).getUrl())
                        .postType(post.getPostType())
                        .reg_date(post.getRegDate())
                        .build()
        ).collect(Collectors.toList());

        PostSlicePageDto postSlicePageDto = PostSlicePageDto.builder()
                //todo Collections.singleton 공부
                .content(Collections.singletonList(postListInfoDtoList))
                .pageNumber(postList.getPageable().getPageNumber())
                .pageFirst(postList.isFirst())
                .pageLast(postList.isLast()).build();
        return postSlicePageDto;
    }

    @Override
    public Object getOtherPostAllList(Long cornId, Pageable pageable) {
        Slice<Post> postList = iPostRepository.findByCornIdOrderByRegDateDesc(cornId, pageable);
        List<PostListInfoDto> postListInfoDtoList = postList.stream().map(post ->
                PostListInfoDto.builder()
                        .postId(post.getId())
                        .postImg(iPostImgRepository.findOneByPostIdAndIdx(post.getId(), 1).getUrl())
                        .postType(post.getPostType())
                        .reg_date(post.getRegDate())
                        .build()
        ).collect(Collectors.toList());

        PostSlicePageDto postSlicePageDto = PostSlicePageDto.builder()
                //todo Collections.singleton 공부
                .content(Collections.singletonList(postListInfoDtoList))
                .pageNumber(postList.getPageable().getPageNumber())
                .pageFirst(postList.isFirst())
                .pageLast(postList.isLast()).build();

//        List<PostListInfoDto> postListInfoDtoList = new ArrayList<>();
//        for (Post post : postList) {
//            PostImg postImg = iPostImgRepository.findOneByPostIdAndIdx(post.getId(), 1);
//            postListInfoDtoList.add(PostListInfoDto.builder().postId(post.getId())
//                    .postImg(postImg.getUrl())
//                    .postType(post.getPostType())
//                    .reg_date(post.getRegDate())
//                    .build());
//        }
        return postSlicePageDto;
    }

    @Override
    public Object getMyPostProductList(Long userId, Pageable pageable) {
        Long cornId = iCornRepository.findByUserId(userId).orElseThrow().getId();
        List<PostType> postTypeList = new ArrayList<>();
        postTypeList.add(SALE);
        postTypeList.add(DISCONTINUED);
        postTypeList.add(SOLD_OUT);
        List<PostListInfoDto> postListInfoDtoList = postRepositoryCustom.PostProductListInfo(postTypeList, cornId, pageable);
        Boolean isLast = postListInfoDtoList.size() < pageable.getPageSize();
        if (!isLast) postListInfoDtoList.remove(pageable.getPageSize() + 1);
        Boolean isFirst = pageable.getPageNumber() == 0;
        return PostSlicePageDto.builder()
                .content(Collections.singletonList(postListInfoDtoList))
                .pageNumber(pageable.getPageNumber())
                .pageFirst(isFirst)
                .pageLast(isLast)
                .build();
    }

    @Override
    public Object getOtherPostProductList(Long cornId, Pageable pageable) {
        List<PostType> postTypeList = new ArrayList<>();
        postTypeList.add(SALE);
        postTypeList.add(DISCONTINUED);
        postTypeList.add(SOLD_OUT);
        List<PostListInfoDto> postListInfoDtoList = postRepositoryCustom.PostProductListInfo(postTypeList, cornId, pageable);
        Boolean isFirst = pageable.getPageNumber() == 0;
        Boolean isLast = postListInfoDtoList.size() < pageable.getPageSize();
        if (!isLast) postListInfoDtoList.remove(pageable.getPageSize() + 1);
        return PostSlicePageDto.builder()
                .content(Collections.singletonList(postListInfoDtoList))
                .pageNumber(pageable.getPageNumber())
                .pageFirst(isFirst)
                .pageLast(isLast)
                .build();
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
    public Object getMyPostStyleList(Long userId, Pageable pageable) {
        Long cornId = iCornRepository.findByUserId(userId).orElseThrow().getId();
        Slice<Post> postList = iPostRepository.findByCornIdAndPostTypeOrderByRegDateDesc(cornId, STYLE, pageable);
        List<PostListInfoDto> postListInfoDtoList = postList.stream().map(post -> PostListInfoDto.builder()
                .postId(post.getId())
                .postImg(iPostImgRepository.findUrlByPostId(post.getId()))
                .postType(post.getPostType())
                .reg_date(post.getRegDate())
                .build()).collect(Collectors.toList());
        PostSlicePageDto postSlicePageDto = PostSlicePageDto.builder()
                .content(Collections.singletonList(postListInfoDtoList))
                .pageNumber(postList.getNumber())
                .pageFirst(postList.isFirst())
                .pageLast(postList.isLast())
                .build();
        return postSlicePageDto;
    }

    @Override
    public Object getOtherPostStyleList(Long cornId, Pageable pageable) {
        Slice<Post> postList = iPostRepository.findByCornIdAndPostTypeOrderByRegDateDesc(cornId, STYLE, pageable);
        List<PostListInfoDto> postListInfoDtoList = postList.stream().map(post -> PostListInfoDto.builder()
                .postId(post.getId())
                .postImg(iPostImgRepository.findUrlByPostId(post.getId()))
                .postType(post.getPostType())
                .reg_date(post.getRegDate())
                .build()).collect(Collectors.toList());
//        List<PostListInfoDto> postListInfoDtoList = new ArrayList<>();
//        for (Post post : postList) {
//            PostImg postImg = iPostImgRepository.findOneByPostIdAndIdx(post.getId(), 1);
//            postListInfoDtoList.add(PostListInfoDto.builder().postId(post.getId())
//                    .postImg(postImg.getUrl())
//                    .postType(post.getPostType())
//                    .reg_date(post.getRegDate())
//                    .build());
//        }
        PostSlicePageDto postSlicePageDto = PostSlicePageDto.builder()
                .content(Collections.singletonList(postListInfoDtoList))
                .pageNumber(postList.getNumber())
                .pageFirst(postList.isFirst())
                .pageLast(postList.isLast())
                .build();
        return postSlicePageDto;
    }

    @Override
    public Object getModPostInfo(Long userId, Long postId) {
        Post post = iPostRepository.findById(postId).orElseThrow();
        List<String> colorList = List.of(post.getColor().split(","));
        List<PostTag> postTagList = iPostTagRepository.findByPostId(postId);
        List<String> postTagNameList = postTagList.stream().map(postTag -> "#" + postTag.getDsc()).collect(Collectors.toList());
        List<PostAndLook> postAndLookList = iPostAndLookRepository.findByPostId(postId);
        List<String> postAndLookNameList = postAndLookList.stream().map(postAndLook -> postAndLook.getLook().getName()).collect(Collectors.toList());
        List<PostImg> postImgList = iPostImgRepository.findByPostId(postId);
        List<String> postImgUrlList = postImgList.stream().map(postImg -> postImg.getUrl()).collect(Collectors.toList());
        PostModInfoDto postModInfoDto = PostModInfoDto.builder()
                .postImgList(postImgUrlList)
                .title(post.getTitle())
                .dsc(post.getDsc())
                .postTagNameList(postTagNameList)
                .postType(post.getPostType())
                .postCategoryName(post.getPostCategory().getName())
                .conditions(post.getConditions())
                .lookList(postAndLookNameList)
                .color(colorList)
                .price(post.getPrice())
                .productSize(post.getProductSize())
                .build();
        return postModInfoDto;
    }

    @Override
    public Object getPostDetailInfo(Long postId) {
        Post post = iPostRepository.findById(postId).orElseThrow();
        List<String> colorList = List.of(post.getColor().split(","));
        List<PostTag> postTagList = iPostTagRepository.findByPostId(postId);
        List<String> postTagDscList = postTagList.stream().map(postTag -> "#" + postTag.getDsc()).collect(Collectors.toList());
        List<PostAndLook> postAndLookList = iPostAndLookRepository.findByPostId(postId);
        List<Long> postAndLookIdList = postAndLookList.stream().map(postAndLook -> postAndLook.getLook().getId()).collect(Collectors.toList());
        PostDetailInfoDto postDetailInfoDto = PostDetailInfoDto.builder()
                .title(post.getTitle())
                .dsc(post.getDsc())
                .postTagList(postTagDscList)
                .postCategoryId(post.getPostCategory().getId())
                .postType(post.getPostType())
                .LookId(postAndLookIdList)
                .colorList(colorList)
                .productSize(post.getProductSize())
                .condition(post.getConditions()).build();
        return postDetailInfoDto;
    }

    @Override
    public PostRecommendListResponseDto getPostCoolListOfNonUser(HttpServletRequest request, Pageable pageable) {
        if(isUser(request)){
            Long userPkId = JwtProvider.getUserPkId(request);
          return  postRepositoryCustom.getPostCoolListOfUser(userPkId, pageable);
        }

        return postRepositoryCustom.getPostCoolListOfNonUser(pageable);
    }

    private boolean isUser(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.hasText(token)){
            if(JwtProvider.validateToken(token))
                return true;
            throw new UniquOneServiceException(ExceptionCode.INVALID_TOKEN, HttpStatus.OK);
        }

        return false;
    }

}
