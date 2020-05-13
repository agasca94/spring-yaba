package com.gasca.yaba.controllers;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import com.gasca.yaba.dto.RegisterRequest;
import com.gasca.yaba.models.Profile;
import com.gasca.yaba.repositories.ProfilesRepository;
import com.gasca.yaba.security.ProfileDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class AuthController {
    
    @Autowired
    private ProfilesRepository profilesRepository;

    @PostMapping("/register")
    public Profile register(@Valid @RequestBody RegisterRequest registerRequest) {
        Profile user = profilesRepository.save(
            new Profile(
                registerRequest.getName(), 
                registerRequest.getBio(), 
                registerRequest.getUsername(), 
                registerRequest.getPassword(), 
                null
            ));

        return user;
    }

    @GetMapping(value="/me")
    public Profile getMethodName(@AuthenticationPrincipal ProfileDetails pd) {
        return pd.getProfile();
    }
}
