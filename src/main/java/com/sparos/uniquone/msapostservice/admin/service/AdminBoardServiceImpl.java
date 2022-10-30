package com.sparos.uniquone.msapostservice.admin.service;

import com.sparos.uniquone.msapostservice.admin.domain.AdminBoard;
import com.sparos.uniquone.msapostservice.admin.domain.BoardType;
import com.sparos.uniquone.msapostservice.admin.dto.request.AdminBoardRequestDto;
import com.sparos.uniquone.msapostservice.admin.dto.request.AdminUpdateBoardTypeDto;
import com.sparos.uniquone.msapostservice.admin.dto.response.AdminBoardResponseDto;
import com.sparos.uniquone.msapostservice.admin.dto.response.AdminBoardResponseListDto;
import com.sparos.uniquone.msapostservice.admin.dto.response.AdminCreateBoardResponseDto;
import com.sparos.uniquone.msapostservice.admin.repository.AdminBoardRepository;
import com.sparos.uniquone.msapostservice.admin.repository.AdminBoardRepositorySupport;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import com.sparos.uniquone.msapostservice.util.response.ExceptionCode;
import com.sparos.uniquone.msapostservice.util.response.UniquOneServiceException;
import com.sparos.uniquone.msapostservice.util.s3.AwsS3UploaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminBoardServiceImpl implements AdminBoardService {

    private final AdminBoardRepository boardRepository;

    private final AdminBoardRepositorySupport boardRepositorySupport;

    private final AwsS3UploaderService awsS3UploaderService;


    @Override
    @Transactional
    public AdminCreateBoardResponseDto createBoard(AdminBoardRequestDto adminBoardRequestDto, MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        //토큰 겟
        Long userPkId = JwtProvider.getUserPkId(request);
        //유저 검증 .. 흠 전에 어드민을 검증하니 패스해야되나.
        //저장
        AdminBoard adminBoard = AdminBoard.builder()
                .writerId(userPkId)
                .boardType(BoardType.ING)
                .title(adminBoardRequestDto.getTitle())
                .subTitle(adminBoardRequestDto.getSubTitle())
                .imgUrl(multipartFile.isEmpty() ? null : awsS3UploaderService.upload(multipartFile, "uniquoneimg", "img"))
                .build();

        boardRepository.save(adminBoard);

        return new AdminCreateBoardResponseDto(adminBoard.getWriterId(), adminBoard.getTitle()
                , adminBoard.getSubTitle(), adminBoard.getImgUrl(), adminBoard.getBoardType());
    }

    @Override
    @Transactional
    public AdminBoardResponseDto updateBoard(Long boardId, AdminBoardRequestDto adminboardrequestDto, MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        Long userPkId = JwtProvider.getUserPkId(request);

        AdminBoard adminBoard = boardRepository.findById(boardId).orElseThrow(() -> {
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.OK);
        });
        BoardType boardType = BoardType.valueOf(adminboardrequestDto.getBoardType());

        AdminBoardResponseDto adminBoardResponseDto = adminBoard.updateAdminBoard(boardType,userPkId ,adminBoard.getTitle(), adminBoard.getSubTitle()
                , multipartFile.isEmpty() ? adminBoard.getImgUrl() : awsS3UploaderService.upload(multipartFile, "uniquoneimg", "img"));

        boardRepository.save(adminBoard);

        return adminBoardResponseDto;
    }

    @Override
    @Transactional
    public AdminBoardResponseDto updateBoardType(Long boardId, AdminUpdateBoardTypeDto adminUpdateBoardTypeDto, HttpServletRequest request) {
        Long userPkId = JwtProvider.getUserPkId(request);

        AdminBoard adminBoard = boardRepository.findById(boardId).orElseThrow(() -> {
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.OK);
        });

//        BoardType boardType = BoardType.valueOf(adminUpdateBoardTypeDto.getBoardType());

//        adminBoard.setBoardType(boardType);
        adminBoard.setBoardType(adminUpdateBoardTypeDto.getBoardType());

        boardRepository.save(adminBoard);

        return new AdminBoardResponseDto(boardId, userPkId
                , adminUpdateBoardTypeDto.getBoardType(),adminBoard.getTitle(),adminBoard.getSubTitle(),adminBoard.getImgUrl());
    }

    @Override
    public AdminBoardResponseDto getBoardDetailInfo(Long boardId) {
        AdminBoard adminBoard = boardRepository.findById(boardId).orElseThrow(() -> {
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.OK);
        });
        return new AdminBoardResponseDto(adminBoard.getId(),adminBoard.getUpdateId() == null ? adminBoard.getWriterId() : adminBoard.getUpdateId()
                ,adminBoard.getBoardType(),adminBoard.getTitle(),adminBoard.getSubTitle(),adminBoard.getImgUrl());
    }

    @Override
    public AdminBoardResponseListDto getBoardList(Pageable pageable) {
        return boardRepositorySupport.findAllAdminBoard(pageable);

    }

    @Override
    @Transactional
    public String deleteBoard(Long boardId, HttpServletRequest request) {

        AdminBoard adminBoard = boardRepository.findById(boardId).orElseThrow(() -> {
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.OK);
        });

        boardRepository.delete(adminBoard);
        return "success admin board";
    }
}
