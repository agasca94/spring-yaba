package com.gasca.yaba.repositories;

import com.gasca.yaba.models.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends PagingAndSortingRepository<Post, Long>{
    public Page<Post> findByAuthorUsername(String username, Pageable pageable);
}
