package com.sparos.uniquone.msapostservice.corn.service;

import com.sparos.uniquone.msapostservice.corn.dto.CornCreateDto;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ICornService {

    void AddCorn(CornCreateDto cornCreateDto, MultipartFile multipartFile) throws IOException;


}
