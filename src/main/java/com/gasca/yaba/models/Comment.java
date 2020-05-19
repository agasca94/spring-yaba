package com.gasca.yaba.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.hateoas.server.core.Relation;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Relation(collectionRelation = "comments")
public class Comment extends Auditable {
    
    @Id @GeneratedValue
    private Long id;
    
    private String content;

    @ManyToOne
    private Profile author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Post post;

    public Comment() {}

    public Comment(String content, Profile author, Post post) {
        this.content = content;
        this.author = author;
        this.post = post;
    }
}
