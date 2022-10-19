package com.sparos.uniquone.msapostservice.post.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface SearchPostRepository {

    Page<Object[]> searchPostPage(String keyword, Pageable pageable);

    Slice<Object[]> searchPostPageBySlice(String keyword, Pageable pageable);

    Page<Object[]> searchPostPage(String type, String keyword, Pageable pageable);
}
