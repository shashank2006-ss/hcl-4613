package com.hcld2.jwtdemo.controller;

import com.hcld2.jwtdemo.entity.Post;
import com.hcld2.jwtdemo.repository.PostRepository;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/posts")

@AllArgsConstructor
public class PostController {
    private final PostRepository posts;

    @GetMapping
    public List<Post> all(){
        return posts.findAllByOrderByCreatedAtDesc();
    }

    @PostMapping
    @SecurityRequirement(name = "jwt-demo-api")
    public ResponseEntity<Post> create(@Valid @RequestBody Post req, Authentication auth){
        Post savedPost = posts.save(
                new Post(null, req.getTitle(), req.getContent(), auth.getName(), LocalDateTime.now())
        );
        return ResponseEntity.ok(savedPost);
    }
}
