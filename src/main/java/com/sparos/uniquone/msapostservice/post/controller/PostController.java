package com.sparos.uniquone.msapostservice.post.controller;

import com.sparos.uniquone.msapostservice.post.dto.PostInputDto;
import com.sparos.uniquone.msapostservice.post.service.IPostService;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/reg/{userId}")
    public ResponseEntity<SuccessResponse> addPost(@PathVariable("userId") Long userId, @RequestPart PostInputDto postInputDto, @RequestPart(value = "imgfilelist", required = false) List<MultipartFile> multipartFileList) throws IOException {
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,iPostService.addPost(postInputDto,multipartFileList,userId)));
    }

    @PatchMapping("/mod/{postId}/{userId}")
    public ResponseEntity<SuccessResponse> modPost(@PathVariable("postId") Long postId, @PathVariable("userId") Long userId,@RequestBody PostInputDto postInputDto){
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,iPostService.modifyPost(postInputDto,userId,postId)));
    }

    @DeleteMapping("/del/{postId}/{userId}")
    public ResponseEntity<SuccessResponse> delPost(@PathVariable("postId") Long postId, @PathVariable("userId") Long userId){
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,iPostService.delPost(postId,userId)));
    }

    @GetMapping("/listall/{cornId}/{userId}")
    public ResponseEntity<SuccessResponse> getOtherUserPostAllListInfo(@PathVariable("cornId") Long cornId, @PathVariable("userId") Long userId){
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,iPostService.getOtherPostAllList(cornId, userId)));
    }

    @GetMapping("/listproduct/{cornId}/{userId}")
    public ResponseEntity<SuccessResponse> getOtherUserPostProductListInfo(@PathVariable("cornId") Long cornId, @PathVariable("userId") Long userId){
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,iPostService.getOtherPostProductList(cornId, userId)));
    }

    @GetMapping("/liststyle/{cornId}/{userId}")
    public ResponseEntity<SuccessResponse> getOtherUserPostStyleListInfo(@PathVariable("cornId") Long cornId, @PathVariable("userId") Long userId){
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,iPostService.getOtherPostStyleList(cornId, userId)));
    }

    @GetMapping("/mod/{postId}")
    public ResponseEntity<SuccessResponse> getModPostInfo(@PathVariable("postId") Long postId, HttpServletRequest httpServletRequest){
        Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,iPostService.getModPostInfo(userPkId,postId)));
    }

}
