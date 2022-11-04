package com.sparos.uniquone.msapostservice.post.controller;

import com.sparos.uniquone.msapostservice.post.dto.PostInputDto;
import com.sparos.uniquone.msapostservice.post.service.IPostService;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(token)) {
            if (JwtProvider.validateToken(token)) ;
            Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
            return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iPostService.addPost(postInputDto, multipartFileList, userPkId)));
        }
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_NOT_TOKEN_CODE, "토큰이없습니다."));
    }

    @PatchMapping("/mod/{postId}")
    public ResponseEntity<SuccessResponse> modPost(@PathVariable("postId") Long postId, HttpServletRequest httpServletRequest, @RequestBody PostInputDto postInputDto) {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(token)) {
            if (JwtProvider.validateToken(token)) ;
            Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
            return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iPostService.modifyPost(postInputDto, userPkId, postId)));
        }
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_NOT_TOKEN_CODE, "토큰이없습니다."));
    }

    @DeleteMapping("/del/{postId}")
    public ResponseEntity<SuccessResponse> delPost(@PathVariable("postId") Long postId, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(token)) {
            if (JwtProvider.validateToken(token)) ;
            Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
            return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iPostService.delPost(postId, userPkId)));
        }
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_NOT_TOKEN_CODE, "토큰이없습니다."));
    }

    //마이포스트리스트
    @GetMapping("/mylistall")
    public ResponseEntity<SuccessResponse> getMyPostAllListInfo(HttpServletRequest httpServletRequest, @PageableDefault(size = 20) Pageable pageable) {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(token)) {
            if (JwtProvider.validateToken(token)) ;
            Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
            return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iPostService.getMyPostAllList(userPkId, pageable)));
        }
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_NOT_TOKEN_CODE, "토큰이없습니다."));
    }

    @GetMapping("/listall/{cornId}")
    public ResponseEntity<SuccessResponse> getOtherPostAllListInfo(@PathVariable("cornId") Long cornId, HttpServletRequest httpServletRequest, @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iPostService.getOtherPostAllList(cornId, pageable)));
    }

    @GetMapping("/mylistproduct")
    public ResponseEntity<SuccessResponse> getMyOUserPostProductListInfo(HttpServletRequest httpServletRequest, @PageableDefault(size = 20) Pageable pageable) {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(token)) {
            if (JwtProvider.validateToken(token)) ;
            Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
            return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iPostService.getMyPostProductList(userPkId, pageable)));
        }
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_NOT_TOKEN_CODE, "토큰이없습니다."));
    }

    @GetMapping("/listproduct/{cornId}")
    public ResponseEntity<SuccessResponse> getOtherUserPostProductListInfo(@PathVariable("cornId") Long cornId, HttpServletRequest httpServletRequest, @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iPostService.getOtherPostProductList(cornId, pageable)));
    }

    @GetMapping("/myliststyle")
    public ResponseEntity<SuccessResponse> getMyUserPostStyleListInfo(HttpServletRequest httpServletRequest, @PageableDefault(size = 20) Pageable pageable) {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(token)) {
            if (JwtProvider.validateToken(token)) ;
            Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
            return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iPostService.getMyPostStyleList(userPkId, pageable)));
        }
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_NOT_TOKEN_CODE, "토큰이없습니다."));
    }

    @GetMapping("/liststyle/{cornId}")
    public ResponseEntity<SuccessResponse> getOtherUserPostStyleListInfo(@PathVariable("cornId") Long cornId, HttpServletRequest httpServletRequest, @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iPostService.getOtherPostStyleList(cornId, pageable)));
    }

    @GetMapping("/mod/{postId}")
    public ResponseEntity<SuccessResponse> getModPostInfo(@PathVariable("postId") Long postId, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(token)) {
            if (JwtProvider.validateToken(token)) ;
            Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
            return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iPostService.getModPostInfo(userPkId, postId)));
        }
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_NOT_TOKEN_CODE, "토큰이없습니다."));
    }

    @GetMapping("/detail/{postId}")
    public ResponseEntity<SuccessResponse> getPostDetailInfo(HttpServletRequest httpServletRequest,@PathVariable("postId") Long postId) {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(token)) {
            if (JwtProvider.validateToken(token)) ;
            Long userPkId = JwtProvider.getUserPkId(httpServletRequest);
            return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iPostService.getUserPostDetailInfo(userPkId,postId)));
        }
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iPostService.getPostDetailInfo(postId)));
    }

    //추천순 상품.
    @GetMapping("/cool")
    public ResponseEntity<SuccessResponse> getPostCoolList(HttpServletRequest request, Pageable pageable) {
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, iPostService.getPostCoolListOfNonUser(request, pageable)));
    }

}
