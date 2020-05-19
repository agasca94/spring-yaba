package com.gasca.yaba.models;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gasca.yaba.security.ProfileDetails;

import org.springframework.hateoas.server.core.Relation;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Relation(collectionRelation = "posts")
public class Post extends Auditable {
    
    @Id @GeneratedValue
    private Long id;
    
    private String title;
    
    private String description;
    
    private String content;

    public int getFavoritesCount() {
        return likedBy.size();
    }

    public boolean getIsFavorited() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        Profile currentUser = ((ProfileDetails) authentication.getPrincipal()).getProfile();

        return likedBy.contains(currentUser);
    }

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Profile author;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "favorites",
        joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "profile_id", referencedColumnName = "id")
    )
    @JsonIgnore
    private Set<Profile> likedBy;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Comment> comments;

    public Post() {}

    public Post(String title, String description, String content, Profile author) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.author = author;
    }
}
