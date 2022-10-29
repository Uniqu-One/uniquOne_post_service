package com.sparos.uniquone.msapostservice.unistar.service;

import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.post.repository.IPostRepository;
import com.sparos.uniquone.msapostservice.unistar.domain.UniStar;
import com.sparos.uniquone.msapostservice.unistar.dto.request.UniStarRequestDto;
import com.sparos.uniquone.msapostservice.unistar.dto.response.UniStarResponseDto;
import com.sparos.uniquone.msapostservice.unistar.repository.IUniStarRepository;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import com.sparos.uniquone.msapostservice.util.response.ExceptionCode;
import com.sparos.uniquone.msapostservice.util.response.UniquOneServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class UniStarServiceImpl implements IUniStarService {

    private final IPostRepository postRepository;
    private final IUniStarRepository uniStarRepository;

    @Override
    @Transactional
    public UniStarResponseDto createUniStar(Long postId, HttpServletRequest request) {
        //토큰
        Long userPkId = JwtProvider.getUserPkId(request);
        //포스트 유무 확인
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.OK);
        });
        //해당 포스트에 해당 유저의 유니스타 등록여부 확인
        boolean existsUniStar = uniStarRepository.existsByPostAndUserId(post, userPkId);

        if (existsUniStar == true)
            throw new UniquOneServiceException(ExceptionCode.EXISTS_UNISTAR, HttpStatus.OK);

        //유니스타 등록.
        UniStar uniStar = UniStar.builder()
                .userId(userPkId)
                .post(post)
                .build();
        uniStarRepository.save(uniStar);

        return new UniStarResponseDto(postId, userPkId, 1);
    }

    @Override
    @Transactional
    public UniStarResponseDto modifyUniStar(Long postId, UniStarRequestDto uniStarRequestDto, HttpServletRequest request) {
        //토큰
        Long userPkId = JwtProvider.getUserPkId(request);
        //포스트 유무 확인
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.OK);
        });
        //해당 포스트에 해당 유저의 유니스타 등록여부 확인
        UniStar uniStar = uniStarRepository.findByPostAndUserId(post, userPkId).orElseThrow(() -> {
                    throw new UniquOneServiceException(ExceptionCode.NOTFOUND_POST_USER_UNISTAR, HttpStatus.OK);
                }
        );
        //유니스타 수정
        Integer level = uniStarRequestDto.getLevel();
        if(level >= 3)
            level = 3;

        uniStar.setLevel(level);

        return new UniStarResponseDto(postId,userPkId, level);
    }

    @Override
    @Transactional
    public String deleteUniStar(Long postId, HttpServletRequest request) {
        //토큰
        Long userPkId = JwtProvider.getUserPkId(request);
        //포스트 유무 확인
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.OK);
        });
        //해당 포스트에 해당 유저의 유니스타 등록여부 확인
        UniStar uniStar = uniStarRepository.findByPostAndUserId(post, userPkId).orElseThrow(() -> {
                    throw new UniquOneServiceException(ExceptionCode.NOTFOUND_POST_USER_UNISTAR, HttpStatus.OK);
                }
        );

        //삭제
        uniStarRepository.delete(uniStar);

        return "success remove UniStar";
    }
}
