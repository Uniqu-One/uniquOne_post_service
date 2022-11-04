package com.sparos.uniquone.msapostservice.corn.service;

import com.sparos.uniquone.msapostservice.corn.dto.CornCreateDto;
import com.sparos.uniquone.msapostservice.corn.dto.CornInfoDto;
import com.sparos.uniquone.msapostservice.corn.dto.CornModifyDto;
import com.sparos.uniquone.msapostservice.corn.dto.CornRandomNickNameDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface ICornService {

    Object addCorn(CornCreateDto cornCreateDto, HttpServletRequest request , MultipartFile multipartFile) throws IOException;

    CornInfoDto getMyCornInfo(Long userId);

    CornInfoDto userGetOtherCornInfo(Long cornId,Long userId);

    CornInfoDto getOtherCornInfo(Long cornId);

    CornInfoDto getCornInfo(Long userId, Long cornId);

    CornModifyDto getCornModifyInfo(Long userId);

    Object patchCornModifyInfo(CornModifyDto cornModifyDto, MultipartFile multipartFile, Long userId) throws IOException;
    CornRandomNickNameDto generatedNickName();

    Object isCornExistence(Long userId);
}
