package com.sparos.uniquone.msapostservice.admin.controller;

import com.sparos.uniquone.msapostservice.admin.dto.request.AdminBoardRequestDto;
import com.sparos.uniquone.msapostservice.admin.dto.request.AdminUpdateBoardTypeDto;
import com.sparos.uniquone.msapostservice.admin.dto.request.ThisSeasonPostRequestDto;
import com.sparos.uniquone.msapostservice.admin.dto.request.ThisWeekUniqueOneRequestDto;
import com.sparos.uniquone.msapostservice.admin.repository.ThisWeekUniqueOneSupport;
import com.sparos.uniquone.msapostservice.admin.service.AdminBoardService;
import com.sparos.uniquone.msapostservice.admin.service.ThisSeasonPostService;
import com.sparos.uniquone.msapostservice.admin.service.ThisWeekUniqueOneService;
import com.sparos.uniquone.msapostservice.qna.dto.AnswerInputDto;
import com.sparos.uniquone.msapostservice.qna.service.IQnAService;
import com.sparos.uniquone.msapostservice.report.service.IReportService;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/admin")
@RestController
@Slf4j
@RequiredArgsConstructor
public class AdminController {
    private final AdminBoardService adminBoardService;
    private final IQnAService iQnAService;
    private final IReportService iReportService;
    private final ThisSeasonPostService thisSeasonPostService;
    private final ThisWeekUniqueOneSupport support;
    private final ThisWeekUniqueOneService thisWeekUniqueOneService;

    @PostMapping("/nBoard")
    public ResponseEntity<?> createBoard(@RequestPart AdminBoardRequestDto adminBoardRequestDto, @RequestPart MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //?????? ?????? ???????????? ????????? Response??? ??????????????? ?????? ?????????
        if (!JwtProvider.getUserRole(request).equals("ROLES_ADMIN")) {
            //????????? ??????????????? ?????? ????????? ???????????????. ????????? ????????? ????????????.
            response.sendRedirect("/notFoundPage");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("???????????? ?????? ??? ????????????.");
        }
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, adminBoardService.createBoard(adminBoardRequestDto, multipartFile, request)));
    }

    @PutMapping("/nBoard/{boardId}")
    public ResponseEntity<SuccessResponse> updateBoard(@PathVariable Long boardId, @RequestPart AdminBoardRequestDto adminBoardRequestDto, @RequestPart MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //?????? ??????.
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, adminBoardService.updateBoard(boardId, adminBoardRequestDto, multipartFile, request)));
    }

    @PatchMapping("/nBoard/{boardId}")
    public ResponseEntity<SuccessResponse> updateBoardType(@PathVariable Long boardId, @RequestBody AdminUpdateBoardTypeDto adminUpdateBoardTypeDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //?????? ??????.
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, adminBoardService.updateBoardType(boardId, adminUpdateBoardTypeDto, request)));
    }

    @GetMapping("/nBoard/{boardId}")
    public ResponseEntity<SuccessResponse> getBoardDetailInfo(@PathVariable Long boardId) {
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, adminBoardService.getBoardDetailInfo(boardId)));
    }

    //????????? ? ?????? ??????
    @GetMapping("/nBoard/all")
    public ResponseEntity<SuccessResponse> getBoardList(Pageable pageable) {
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, adminBoardService.getBoardList(pageable)));
    }

    @DeleteMapping("/nBoard/{boardId}")
    public ResponseEntity<SuccessResponse> deleteBoard(@PathVariable Long boardId, HttpServletRequest request, HttpServletResponse response) {
        //?????? ??????.
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, adminBoardService.deleteBoard(boardId, request)));
    }

    // ?????? ?????? ????????? ??????
    @GetMapping("/qna/all/{pageNum}")
    public ResponseEntity<SuccessResponse> findAllQnA(@PathVariable int pageNum) {
        JSONObject jsonObject = iQnAService.findAllQnA(pageNum);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }

    // ?????? ?????? ??????
    @GetMapping("/qna/{qnaId}")
    public ResponseEntity<SuccessResponse> findDetailQnA(@PathVariable Long qnaId) {
        JSONObject jsonObject = iQnAService.findDetailQnA(qnaId);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }

    // ?????? ?????? ??????
    @PatchMapping("/qna")
    public ResponseEntity<SuccessResponse> createAnswer(@RequestBody AnswerInputDto answerInputDto) {
        JSONObject jsonObject = iQnAService.createAnswer(answerInputDto);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }

    // ?????? ?????? ????????? ??????
    @GetMapping("/report")
    public ResponseEntity<SuccessResponse> findAllReport() {
        JSONObject jsonObject = iReportService.findAllReport();
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }

    @PostMapping("/thisSeason")
    public ResponseEntity<SuccessResponse> createSeasonPostList(ThisSeasonPostRequestDto thisSeasonPostRequestDto){
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, thisSeasonPostService.createThisSeasonPost(thisSeasonPostRequestDto)));
    }


    @GetMapping("/thisSeason/{season}")
    public ResponseEntity<SuccessResponse> getSeasonPostList(@PathVariable String season, Pageable pageable){
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE,thisSeasonPostService.getSeasonPostList(season, pageable)));
    }



    @PostMapping("/thisWeek")
    public ResponseEntity<SuccessResponse> createThisWeekUniqueOne(ThisWeekUniqueOneRequestDto requestDto){
        if(requestDto.getCornId().size() == 0)
            return null;

        return ResponseEntity.ok(
                SuccessResponse.of(SuccessCode.SUCCESS_CODE, thisWeekUniqueOneService.createThisWeekUniqueOne(requestDto))
        );

    }

    @GetMapping("/thisWeek")
    public ResponseEntity<SuccessResponse> getThisWeekUniqueOne(){
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, support.getUniqueOneInfo()));
    }

}
