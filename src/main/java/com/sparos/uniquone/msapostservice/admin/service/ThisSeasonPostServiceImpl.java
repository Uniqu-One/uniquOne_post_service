package com.sparos.uniquone.msapostservice.admin.service;

import com.sparos.uniquone.msapostservice.admin.domain.ThisSeasonPost;
import com.sparos.uniquone.msapostservice.admin.dto.request.ThisSeasonPostRequestDto;
import com.sparos.uniquone.msapostservice.admin.dto.response.ThisSeasonPostResponseDto;
import com.sparos.uniquone.msapostservice.admin.repository.ThisSeasonPostRepository;
import com.sparos.uniquone.msapostservice.admin.repository.ThisSeasonPostSupport;
import com.sparos.uniquone.msapostservice.admin.type.SeasonType;
import com.sparos.uniquone.msapostservice.post.repository.IPostRepository;
import com.sparos.uniquone.msapostservice.util.response.ExceptionCode;
import com.sparos.uniquone.msapostservice.util.response.UniquOneServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ThisSeasonPostServiceImpl implements ThisSeasonPostService {

    private final IPostRepository postRepository;

    private final ThisSeasonPostRepository thisSeasonPostRepository;

    private final ThisSeasonPostSupport thisSeasonPostSupport;

    @Override
    @Transactional
    public String createThisSeasonPost(ThisSeasonPostRequestDto requestDto) {

        if (requestDto.getPostId().size() == 0)
            return "post is Empty";

        requestDto.getPostId().forEach(postId -> {
            postRepository.findById(postId).orElseThrow(() -> {
                throw new UniquOneServiceException(ExceptionCode.NOTFOUND_POST, HttpStatus.OK);
            });

            ThisSeasonPost thisSeasonPost = ThisSeasonPost.builder()
                    .postId(postId)
                    .seasonType(SeasonType.valueOf(requestDto.getSeasonType()))
                    .build();

            thisSeasonPostRepository.save(thisSeasonPost);
        });

        return "Success ThisSeasonPost List";
    }

    @Override
    public List<ThisSeasonPostResponseDto> getSeasonPostList(String seasonType, Pageable pageable) {
        return thisSeasonPostSupport.getSeasonPostList(SeasonType.valueOf(seasonType), pageable);
    }
}
