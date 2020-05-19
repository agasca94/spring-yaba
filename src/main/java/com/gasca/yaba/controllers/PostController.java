package com.gasca.yaba.controllers;

import javax.validation.Valid;

import com.gasca.yaba.security.ProfileDetails;
import com.gasca.yaba.assemblers.PostAssembler;
import com.gasca.yaba.dto.CreatePostRequest;
import com.gasca.yaba.exceptions.ResourceNotFoundException;
import com.gasca.yaba.models.Post;
import com.gasca.yaba.models.Profile;
import com.gasca.yaba.repositories.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/posts")
@Slf4j
public class PostController {
    
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostAssembler postAssembler;

    @Autowired
    private PagedResourcesAssembler<Post> pagesAssembler;

    @GetMapping
    public PagedModel<EntityModel<Post>> findAll(
        @RequestParam(defaultValue = "") String author,
        @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Post> posts = author.isBlank() ?
            postRepository.findAll(pageable) :
            postRepository.findByAuthorUsername(author, pageable);

        return pagesAssembler.toModel(posts, postAssembler);
    } 

    @GetMapping("/{postId}")
    public EntityModel<Post> findOne(@PathVariable Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("post"));

        return postAssembler.toModel(post);
    }

    @PostMapping
    public Post create(
        @AuthenticationPrincipal ProfileDetails pd,
        @Valid @RequestBody CreatePostRequest payload
    ) {
        Profile author = pd.getProfile();

        Post post = postRepository.save(
            new Post(payload.getTitle(), payload.getDescription(), payload.getContent(), author)
        );

        return post;
    }

    @PutMapping("/{postId}")
    @PreAuthorize("hasPermission(#postId, 'post', 'update')")
    public Post update(
        @PathVariable Long postId, 
        @Valid @RequestBody CreatePostRequest payload
    ) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("Post"));

        post.setTitle(payload.getTitle());
        post.setDescription(payload.getDescription());
        post.setContent(payload.getContent());

        postRepository.save(post);
        
        return post;
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasPermission(#postId, 'post', 'delete')")
    public Long delete(@PathVariable Long postId) {
        postRepository.deleteById(postId);
        
        return postId;
    }

    @PostMapping("/{postId}/favorite")
    public Post favorite(
        @AuthenticationPrincipal ProfileDetails pd,
        @PathVariable Long postId
    ) {
        Profile profile = pd.getProfile();

        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("Post"));

        post.getLikedBy().add(profile);

        postRepository.save(post);

        return post;
    }

    @DeleteMapping("/{postId}/favorite")
    public Post unfavorite(
        @AuthenticationPrincipal ProfileDetails pd,
        @PathVariable Long postId
    ) {
        Profile profile = pd.getProfile();

        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("Post"));

        post.getLikedBy().remove(profile);
        
        postRepository.save(post);

        return post;
    }
}
