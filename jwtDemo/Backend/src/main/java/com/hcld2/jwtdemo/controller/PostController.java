package com.hcld2.jwtdemo.controller;

import com.hcld2.jwtdemo.entity.Post;
import com.hcld2.jwtdemo.repository.PostRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/posts")

@AllArgsConstructor
public class PostController {
    private final PostRepository posts;

    @GetMapping
    public List<Post> all(){
        return posts.findAllByOrderByCreatedAtDesc();
    }

    @PostMapping
    public Post create(@Valid @RequestBody Post req, Authentication auth){
        return posts.save(new Post(req.getTitle(),req.getContent(),auth.getName()));
    }
}