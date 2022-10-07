package com.sparos.uniquone.msapostservice.corn.service;

import com.sparos.uniquone.msapostservice.corn.dto.CornCreateDto;

import com.sparos.uniquone.msapostservice.corn.dto.CornInfoDto;
import com.sparos.uniquone.msapostservice.corn.dto.CornUserInfoDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ICornService {

    void AddCorn(CornCreateDto cornCreateDto, MultipartFile multipartFile) throws IOException;
    CornInfoDto GetCornMyInfo(Long userId);
    CornUserInfoDto GetCornUserInfo(Long userId, Long cornId);


}
