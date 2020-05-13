package com.gasca.yaba.repositories;

import com.gasca.yaba.models.Comment;

import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long>{
    
}
