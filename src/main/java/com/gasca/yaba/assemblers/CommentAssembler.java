package com.gasca.yaba.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.gasca.yaba.controllers.ProfileController;
import com.gasca.yaba.models.Comment;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class CommentAssembler implements RepresentationModelAssembler<Comment, EntityModel<Comment>> {

    @Override
    public EntityModel<Comment> toModel(Comment comment) {
        return new EntityModel<Comment>(
            comment,
            linkTo(methodOn(ProfileController.class).findOne(comment.getAuthor().getUsername())).withRel("author")
        );
    }    
}
