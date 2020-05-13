package com.gasca.yaba.controllers;

import com.gasca.yaba.security.ProfileDetails;

import java.util.stream.Collectors;

import com.gasca.yaba.assemblers.CommentAssembler;
import com.gasca.yaba.dto.CommentRequest;
import com.gasca.yaba.exceptions.ResourceNotFoundException;
import com.gasca.yaba.models.Comment;
import com.gasca.yaba.models.Post;
import com.gasca.yaba.models.Profile;
import com.gasca.yaba.repositories.CommentRepository;
import com.gasca.yaba.repositories.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {
    
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postsRepository;

    @Autowired
    private CommentAssembler commentAssembler;

    @GetMapping()
    public Iterable<EntityModel<Comment>> findAll(@PathVariable Long postId) {
        Post post = postsRepository.findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("Post"));

        Iterable<EntityModel<Comment>> comments = post.getComments()
            .stream()
            .map(commentAssembler::toModel)
            .collect(Collectors.toList());

        return comments;
    }

    @PostMapping()
    public Comment create(
        @AuthenticationPrincipal ProfileDetails pd, 
        @PathVariable Long postId,
        @RequestBody CommentRequest payload
    ) {
        Profile author = pd.getProfile();

        Post post = postsRepository.findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("Post"));

        Comment comment =  commentRepository.save(
            new Comment(payload.getContent(), author, post)
        );

        return comment;
    }

    @PutMapping("/{commentId}")
    @PreAuthorize("hasPermission(#commentId, 'comment', 'update')")
    public Comment update(
        @PathVariable Long commentId,
        @RequestBody CommentRequest payload
    ) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new ResourceNotFoundException("Comment"));

        comment.setContent(payload.getContent());

        commentRepository.save(comment);

        return comment;
    }

    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasPermission(#commentId, 'comment', 'delete')")
    public Long delete(@PathVariable Long commentId) {
        commentRepository.deleteById(commentId);

        return commentId;
    }    
}
