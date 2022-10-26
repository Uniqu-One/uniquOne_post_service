package com.sparos.uniquone.msapostservice.post.controller;

import com.sparos.uniquone.msapostservice.post.dto.PostInputDto;
import com.sparos.uniquone.msapostservice.post.service.IPostService;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RequestMapping("/posts")
@RequiredArgsConstructor
@RestController
public class PostController {

    private final IPostService iPostService;

    @PostMapping("/reg")
    public ResponseEntity<SuccessResponse> addPost(HttpServletRequest httpServletRequest, @RequestPart PostInputDto postInputDto, @RequestPart(value = "imgfilelist", required = false) List<MultipartFile> multipartFileList) throws IOException {
        Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iPostService.addPost(postInputDto, multipartFileList, userPkId)));
    }

    @PatchMapping("/mod/{postId}")
    public ResponseEntity<SuccessResponse> modPost(@PathVariable("postId") Long postId,HttpServletRequest httpServletRequest, @RequestBody PostInputDto postInputDto) {
        Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iPostService.modifyPost(postInputDto, userPkId, postId)));
    }

    @DeleteMapping("/del/{postId}")
    public ResponseEntity<SuccessResponse> delPost(@PathVariable("postId") Long postId,HttpServletRequest httpServletRequest) {
        Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iPostService.delPost(postId, userPkId)));
    }

    @GetMapping("/listall/{cornId}")
    public ResponseEntity<SuccessResponse> getOtherUserPostAllListInfo(@PathVariable("cornId") Long cornId,HttpServletRequest httpServletRequest,@PageableDefault(size = 20) Pageable pageable) {
        Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iPostService.getOtherPostAllList(cornId, userPkId,pageable)));
    }

    //마이포스트리스트
    @GetMapping("/mylistall/{cornId}")
    public ResponseEntity<SuccessResponse> getMyPostAllListInfo(@PathVariable("cornId") Long cornId, HttpServletRequest httpServletRequest,@PageableDefault(size = 20) Pageable pageable) {
        Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iPostService.getOtherPostAllList(cornId, userPkId,pageable)));
    }

    @GetMapping("/listproduct/{cornId}")
    public ResponseEntity<SuccessResponse> getOtherUserPostProductListInfo(@PathVariable("cornId") Long cornId,HttpServletRequest httpServletRequest,@PageableDefault(size = 20) Pageable pageable) {
        Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iPostService.getOtherPostProductList(cornId, userPkId, pageable)));
    }

    @GetMapping("/liststyle/{cornId}")
    public ResponseEntity<SuccessResponse> getOtherUserPostStyleListInfo(@PathVariable("cornId") Long cornId,HttpServletRequest httpServletRequest,@PageableDefault(size = 20) Pageable pageable) {
        Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iPostService.getOtherPostStyleList(cornId, userPkId, pageable)));
    }

    @GetMapping("/mod/{postId}")
    public ResponseEntity<SuccessResponse> getModPostInfo(@PathVariable("postId") Long postId, HttpServletRequest httpServletRequest) {
        Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iPostService.getModPostInfo(userPkId, postId)));
    }

    @GetMapping("/detail/{postId}")
    public ResponseEntity<SuccessResponse> getPostDetailInfo(@PathVariable("postId") Long postId){
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iPostService.getPostDetailInfo(postId)));
    }

}
