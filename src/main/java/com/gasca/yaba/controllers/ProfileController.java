package com.gasca.yaba.controllers;

import java.util.Set;

import com.gasca.yaba.assemblers.ProfileAssembler;
import com.gasca.yaba.exceptions.ResourceNotFoundException;
import com.gasca.yaba.models.Post;
import com.gasca.yaba.models.Profile;
import com.gasca.yaba.repositories.ProfilesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {
    
    @Autowired
    private ProfilesRepository profilesRepository;

    @Autowired
    ProfileAssembler profileAssembler;

    @GetMapping("/profiles/{username}")
    public EntityModel<Profile> findOne(@PathVariable String username) {
        Profile profile = profilesRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("Profile"));

        return profileAssembler.toModel(profile);
    }

    @GetMapping("/profiles/{username}/favorites")
    public Set<Post> findAllFavorites(@PathVariable String username) {
        Profile profile = profilesRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("Profile"));

        Set<Post> posts = profile.getLiked();

        return posts;
    }
}
