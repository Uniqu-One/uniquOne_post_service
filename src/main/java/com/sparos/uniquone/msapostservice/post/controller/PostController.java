package com.sparos.uniquone.msapostservice.post.controller;

import com.sparos.uniquone.msapostservice.post.dto.PostInputDto;
import com.sparos.uniquone.msapostservice.post.service.IPostService;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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

    @GetMapping("/{cornId}/{userId}")
    public ResponseEntity<SuccessResponse> getOtherUserPostAllListInfo(@PathVariable("cornId") Long cornId, @PathVariable("userId") Long userId){
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,iPostService.getOtherPostAllList(cornId, userId)));
    }

}
