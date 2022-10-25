package com.sparos.uniquone.msapostservice.post.controller.search;

import com.sparos.uniquone.msapostservice.post.dto.search.request.SearchRequestDto;
import com.sparos.uniquone.msapostservice.post.repository.IPostRepository;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
@Slf4j
public class SearchController {

    private final IPostRepository postRepository;

    @GetMapping("/{keyword}")
    public ResponseEntity<?> searchPost(@PathVariable("keyword") String keyword) {

        return ResponseEntity.status(HttpStatus.OK).body(postRepository.searchPostOfNonUser(keyword, PageRequest.of(0, 10)));
    }

    @GetMapping("/user")
    public ResponseEntity<?> searchPost(SearchRequestDto searchRequestDto, Pageable pageable, HttpServletRequest request) {

        Long userPkId = JwtProvider.getUserPkId(request);

        postRepository.searchPostOfUser(searchRequestDto, userPkId, pageable);
        return null;
//        return ResponseEntity.status(HttpStatus.OK).body(postRepository.fullSearchPostPageOfUser(keyword,4L ,PageRequest.of(0,10)));
    }
}
