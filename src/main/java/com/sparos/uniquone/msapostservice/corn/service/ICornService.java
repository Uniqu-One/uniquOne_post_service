package com.sparos.uniquone.msapostservice.corn.service;

import com.sparos.uniquone.msapostservice.corn.dto.CornCreateDto;
import com.sparos.uniquone.msapostservice.corn.dto.CornInfoDto;
import com.sparos.uniquone.msapostservice.corn.dto.CornModifyDto;
import com.sparos.uniquone.msapostservice.corn.dto.CornRandomNickNameDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ICornService {

    Object AddCorn(CornCreateDto cornCreateDto, MultipartFile multipartFile) throws IOException;

    CornInfoDto GetMyCornInfo(Long userId);

    CornInfoDto GetOtherCornInfo(Long cornId);

    CornInfoDto GetCornInfo(Long userId, Long cornId);

    CornModifyDto GetCornModifyInfo(Long userId);

    Object PatchCornModifyInfo(CornModifyDto cornModifyDto, MultipartFile multipartFile, Long userId) throws IOException;
    CornRandomNickNameDto generatedNickName();
}
