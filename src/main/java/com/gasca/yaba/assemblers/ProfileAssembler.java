package com.gasca.yaba.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.gasca.yaba.controllers.PostController;
import com.gasca.yaba.controllers.ProfileController;
import com.gasca.yaba.models.Profile;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ProfileAssembler implements RepresentationModelAssembler<Profile, EntityModel<Profile>> {

    @Override
    public EntityModel<Profile> toModel(Profile profile) {
        return new EntityModel<Profile>(
            profile,
            linkTo(methodOn(PostController.class).findAll(profile.getUsername(), null)).withRel("posts"),
            linkTo(methodOn(ProfileController.class).findAllFavorites(profile.getUsername())).withRel("favorites"),
            linkTo(methodOn(ProfileController.class).findOne(profile.getUsername())).withSelfRel()
        );
    }   
}
