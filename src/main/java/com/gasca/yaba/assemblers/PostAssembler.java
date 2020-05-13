package com.gasca.yaba.assemblers;

import com.gasca.yaba.controllers.CommentController;
import com.gasca.yaba.controllers.PostController;
import com.gasca.yaba.controllers.ProfileController;
import com.gasca.yaba.models.Post;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PostAssembler implements RepresentationModelAssembler<Post, EntityModel<Post>>{

    @Override
    public EntityModel<Post> toModel(Post post) {
        return new EntityModel<Post>(
            post, 
            linkTo(methodOn(ProfileController.class).findOne(post.getAuthor().getUsername())).withRel("author"),
            linkTo(methodOn(CommentController.class).findAll(post.getId())).withRel("comments"),
            linkTo(methodOn(PostController.class).findOne(post.getId())).withSelfRel()
        );
    }   
}
