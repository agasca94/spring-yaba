package com.gasca.yaba.repositories;

import java.util.Optional;

import com.gasca.yaba.models.Profile;

import org.springframework.data.repository.CrudRepository;

public interface ProfilesRepository extends CrudRepository<Profile, Long>{
    Optional<Profile> findByUsername(String username);
}
