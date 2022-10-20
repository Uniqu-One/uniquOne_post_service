package com.sparos.uniquone.msapostservice.post.controller.search;

import com.sparos.uniquone.msapostservice.post.repository.IPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final IPostRepository postRepository;

    @GetMapping("/{keyword}")
    public ResponseEntity<?> searchPost(@PathVariable("keyword") String keyword){

        return ResponseEntity.status(HttpStatus.OK).body(postRepository.searchPostPage(keyword, PageRequest.of(0,10)));
    }
}
