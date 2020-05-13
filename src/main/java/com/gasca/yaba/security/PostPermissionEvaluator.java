package com.gasca.yaba.security;

import java.io.Serializable;
import java.util.Optional;

import com.gasca.yaba.models.Comment;
import com.gasca.yaba.models.Post;
import com.gasca.yaba.repositories.CommentRepository;
import com.gasca.yaba.repositories.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class PostPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private PostRepository postsRepository;

    @Autowired
    private CommentRepository commentsRepository;

    @Override
    public boolean hasPermission(Authentication arg0, Object targetDomainObject, Object permission) {
        return true;
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
        if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
            return true;
        }
        
        String target = targetType.toLowerCase();

        if (target.equals("post")) {
            Optional<Post> post = postsRepository.findById((Long) targetId);
            
            if (post.isPresent()) {
                return post.get().getAuthor().getUsername().equals(auth.getName());
            }
        } else if (target.equals("comment")) {
            Optional<Comment> comment = commentsRepository.findById((Long) targetId);
            
            if (comment.isPresent()) {
                return comment.get().getAuthor().getUsername().equals(auth.getName());
            }
        }

        return true;
    }    
}
