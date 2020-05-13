package com.gasca.yaba.security;

import com.gasca.yaba.models.Profile;
import com.gasca.yaba.repositories.ProfilesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ProfileDetailsService implements UserDetailsService {

    @Autowired
    private ProfilesRepository profilesRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Profile profile = profilesRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return new ProfileDetails(profile);
    }
}
