package com.gasca.yaba.security;

import java.util.Collection;

import com.gasca.yaba.models.Profile;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class ProfileDetails implements UserDetails {

    private static final long serialVersionUID = -1122639071977212332L;

    private final Profile profile;

    public ProfileDetails(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }

    @Override
    public String getPassword() {
        return profile.getPassword();
    }

    @Override
    public String getUsername() {
        return profile.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }    
}
