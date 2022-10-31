package com.sparos.uniquone.msapostservice.admin.service;

import com.sparos.uniquone.msapostservice.admin.dto.request.AdminBoardRequestDto;
import com.sparos.uniquone.msapostservice.admin.dto.request.AdminUpdateBoardTypeDto;
import com.sparos.uniquone.msapostservice.admin.dto.response.AdminBoardResponseDto;
import com.sparos.uniquone.msapostservice.admin.dto.response.AdminBoardResponseListDto;
import com.sparos.uniquone.msapostservice.admin.dto.response.AdminCreateBoardResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface AdminBoardService {

    AdminCreateBoardResponseDto createBoard(AdminBoardRequestDto adminBoardRequestDto, MultipartFile multipartFile, HttpServletRequest request) throws IOException;

    AdminBoardResponseDto updateBoard(Long boardId, AdminBoardRequestDto adminboardrequestDto, MultipartFile multipartFile, HttpServletRequest request) throws IOException;

    AdminBoardResponseDto updateBoardType(Long boardId,AdminUpdateBoardTypeDto adminUpdateBoardTypeDto, HttpServletRequest request);

    AdminBoardResponseDto getBoardDetailInfo(Long boardId);

    AdminBoardResponseListDto getBoardList(Pageable pageable);

    String deleteBoard(Long boardId, HttpServletRequest request);

}
