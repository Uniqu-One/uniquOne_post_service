package com.sparos.uniquone.msapostservice.corn.service;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.corn.dto.CornCreateDto;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CornServiceImpl implements ICornService{
    private final ModelMapper modelMapper;
    private final ICornRepository iCornRepository;

    @Override
    public void AddCorn(CornCreateDto cornCreateDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Corn corn = modelMapper.map(cornCreateDto, Corn.class);
        iCornRepository.save(corn);
    }
}
