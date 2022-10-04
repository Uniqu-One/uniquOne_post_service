package com.sparos.uniquone.msapostservice.corn.service;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.corn.dto.CornCreateDto;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;

import com.sparos.uniquone.msapostservice.util.s3.AwsS3UploaderService;
n
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor

public class CornServiceImpl implements ICornService {
    private final ModelMapper modelMapper;
    private final ICornRepository iCornRepository;

    private final AwsS3UploaderService awsS3UploaderService;

    @Override
    public void AddCorn(CornCreateDto cornCreateDto, MultipartFile multipartFile) throws IOException {
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        Corn corn = modelMapper.map(cornCreateDto, Corn.class);

        Corn corn = Corn.builder().userId(cornCreateDto.getUserId())
                .title(cornCreateDto.getTitle())
                .dsc(cornCreateDto.getDsc())
                .imageUrl(awsS3UploaderService.upload(multipartFile, "uniquoneimg", "img"))
                .build();


        iCornRepository.save(corn);
    }
}
