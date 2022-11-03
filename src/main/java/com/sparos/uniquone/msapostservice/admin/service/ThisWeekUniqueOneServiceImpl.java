package com.sparos.uniquone.msapostservice.admin.service;

import com.sparos.uniquone.msapostservice.admin.domain.ThisWeekUniqueOne;
import com.sparos.uniquone.msapostservice.admin.dto.request.ThisWeekUniqueOneRequestDto;
import com.sparos.uniquone.msapostservice.admin.repository.ThisWeekUniqueOneRepository;
import com.sparos.uniquone.msapostservice.admin.repository.ThisWeekUniqueOneSupport;
import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import com.sparos.uniquone.msapostservice.util.response.ExceptionCode;
import com.sparos.uniquone.msapostservice.util.response.UniquOneServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ThisWeekUniqueOneServiceImpl implements ThisWeekUniqueOneService{

    private final ThisWeekUniqueOneSupport thisWeekUniqueOneSupport;

    private final ThisWeekUniqueOneRepository thisWeekUniqueOneRepository;

    private final ICornRepository cornRepository;

    @Override
    @Transactional
    public String createThisWeekUniqueOne(ThisWeekUniqueOneRequestDto requestDto) {

        requestDto.getCornId().forEach(corId ->{
            cornRepository.findById(corId).orElseThrow(() -> {
                throw new UniquOneServiceException(ExceptionCode.NOTFOUND_CORN, HttpStatus.OK);
            });

            ThisWeekUniqueOne thisWeekUniqueOne = ThisWeekUniqueOne.builder().id(corId).build();

            thisWeekUniqueOneRepository.save(thisWeekUniqueOne);
        });

        return "Success Register This Week Corn";
    }
}
