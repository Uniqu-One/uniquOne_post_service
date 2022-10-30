package com.sparos.uniquone.msapostservice.admin.controller;

import com.sparos.uniquone.msapostservice.admin.dto.request.AdminBoardRequestDto;
import com.sparos.uniquone.msapostservice.admin.dto.request.AdminUpdateBoardTypeDto;
import com.sparos.uniquone.msapostservice.admin.dto.response.AdminBoardResponseDto;
import com.sparos.uniquone.msapostservice.admin.service.AdminBoardService;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@RequestMapping("/admin/nBoard")
@RestController
@Slf4j
@RequiredArgsConstructor
public class AdminBoardController {
    private final AdminBoardService adminBoardService;

    @PostMapping()
    public ResponseEntity<?> createBoard(@RequestPart AdminBoardRequestDto adminBoardRequestDto,  @RequestPart MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //권한 검사 어드민이 아니면 Response에 리다이렉트 해서 보내기
        if(!JwtProvider.getUserRole(request).equals("ROLES_ADMIN")){
            //에러를 날려야하나 바로 리턴을 때려야하나. 나중에 테스트 해봐야함.
            response.sendRedirect("/notFoundPage");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("페이지를 찾을 수 없습니다.");
        }
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,adminBoardService.createBoard(adminBoardRequestDto,multipartFile,request)));
    }
    @PutMapping("/{boardId}")
    public ResponseEntity<SuccessResponse> updateBoard(@PathVariable Long boardId, @RequestPart AdminBoardRequestDto adminBoardRequestDto, @RequestPart MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response) throws  IOException{
        //권한 검사.

        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,adminBoardService.updateBoard(boardId,adminBoardRequestDto,multipartFile,request)));
    }
    @PatchMapping("/{boardId}")
    public ResponseEntity<SuccessResponse> updateBoardType(@PathVariable Long boardId, @RequestBody AdminUpdateBoardTypeDto adminUpdateBoardTypeDto, HttpServletRequest request, HttpServletResponse response) throws  IOException{
        //권한 검사.


        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,adminBoardService.updateBoardType(boardId, adminUpdateBoardTypeDto,request)));
    }



    @GetMapping("/{boardId}")
    public ResponseEntity<SuccessResponse> getBoardDetailInfo(@PathVariable Long boardId) {
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,adminBoardService.getBoardDetailInfo(boardId)));
    }
    //리스트 ? 아님 생략
    @GetMapping("/all")
    public ResponseEntity<SuccessResponse> getBoardList(Pageable pageable) {
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,adminBoardService.getBoardList(pageable)));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<SuccessResponse> deleteBoard(@PathVariable Long boardId, HttpServletRequest request, HttpServletResponse response) {
        //권한 검사.


        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,adminBoardService.deleteBoard(boardId, request)));
    }

}
