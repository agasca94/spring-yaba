package com.gasca.yaba.models;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@JsonIgnoreProperties({"createdAt", "updatedAt"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class Profile extends Auditable {
    
    public static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Id @GeneratedValue 
    private Long id;
    
    private String name;
    
    private String bio;
    
    private String username;
    
    @JsonIgnore
    private String password;
    
    private String avatar;

    @ManyToMany(mappedBy = "likedBy", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Post> liked;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Post> posts;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Comment> comments;

    public Profile() {}

    public Profile(String name, String bio, String username, String password, String avatar) {
        this.name = name;
        this.bio = bio;
        this.username = username;
        this.avatar = avatar;
        this.setPassword(password);
    }

    public void setPassword(String password) {
        this.password = passwordEncoder.encode(password);
    }
}
