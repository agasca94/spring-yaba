package com.gasca.yaba;

import java.util.Arrays;
import java.util.Set;

import com.gasca.yaba.models.*;
import com.gasca.yaba.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner{

    private final ProfilesRepository profiles;
    private final PostRepository posts;
    private final CommentRepository comments;

    @Autowired
    public DatabaseLoader(ProfilesRepository profilesRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.profiles = profilesRepository;
        this.posts = postRepository;
        this.comments = commentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        
        Profile user1 = this.profiles.save(
            new Profile("Arturo", "Bio", "agasca", "pass", "image.jpg")
        );
        Profile user2 = this.profiles.save(
            new Profile("Andrés", "Bio", "acampos", "pass", "image.jpg")
        );
        Profile user3 = this.profiles.save(
            new Profile("Fernán", "Bio", "fgarcia", "pass", "image.jpg")
        );


        Post p1 = this.posts.save(
            new Post("A", "a", "aaa", user1)
        );

        Post p2 = this.posts.save(
            new Post("B", "b", "bbb", user1)
        );
        
        Post p3 = this.posts.save(
            new Post("C", "c", "c", user2)
        );
        
        Post p4 = this.posts.save(
            new Post("D", "d", "ddd", user2)
        );

        this.posts.save(
            new Post("E", "e", "eee", user3)
        );
        this.posts.save(
            new Post("AA", "aa", "aaaaaa", user1)
        );

        this.comments.saveAll(Arrays.asList(
            new Comment("F", user2, p1),
            new Comment("G", user3, p1),
            new Comment("H", user2, p1),

            new Comment("I", user1, p2),

            new Comment("J", user3, p3),
            new Comment("K", user2, p3),

            new Comment("L", user1, p4)
        ));

        p1.setLikedBy(
            Set.of(user2)
        );
        p2.setLikedBy(
            Set.of(user1, user3)
        );
        p3.setLikedBy(
            Set.of(user1, user2, user3)
        );

        posts.saveAll(
            Arrays.asList(p1, p2, p3)
        );
    }
}
