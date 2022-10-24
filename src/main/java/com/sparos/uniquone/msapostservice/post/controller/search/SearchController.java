package com.sparos.uniquone.msapostservice.post.controller.search;

import com.sparos.uniquone.msapostservice.post.repository.IPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
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
    public ResponseEntity<?> searchPost(@PathVariable("keyword") String keyword){

        return ResponseEntity.status(HttpStatus.OK).body(postRepository.searchPostPageOfNonUser(keyword, PageRequest.of(0,10)));
    }

    @GetMapping("/{keyword}/user")
    public ResponseEntity<?> searchPost(@PathVariable("keyword") String keyword, HttpServletRequest request){
        log.info("non HashTag start");
        postRepository.searchHashTagPageOfNonUser(keyword,PageRequest.of(0,10));
        log.info("non HashTag end");
        log.info("user HashTag start");
        postRepository.searchHashTagPageOfUser(keyword,5L,PageRequest.of(0,10));
        log.info("user HashTag end");
        return null;
//        return ResponseEntity.status(HttpStatus.OK).body(postRepository.searchPostPageOfUser(keyword,4L ,PageRequest.of(0,10)));
    }
}
