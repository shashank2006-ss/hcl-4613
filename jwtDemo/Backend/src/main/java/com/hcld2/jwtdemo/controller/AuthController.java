package com.hcld2.jwtdemo.controller;

import com.hcld2.jwtdemo.dto.AuthRequest;
import com.hcld2.jwtdemo.dto.AuthResponse;
import com.hcld2.jwtdemo.entity.User;
import com.hcld2.jwtdemo.repository.UserRepository;
import com.hcld2.jwtdemo.service.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthRequest req) {
        if(users.findByUsername(req.username()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("error", "username is already in use")
            );
        }
        users.save(new User(req.username(),encoder.encode(req.password()));
        return ResponseEntity.ok(Map.of("message", "user registered successfully"));
    }
    @PostMapping("/login")
    public ResponseEntity<?>login(@Valid @RequestBody AuthRequest req){
        var user=users.findByUsername(req.username()).orElse(null);
        if(user==null || !encoder.matches(req.password(), user.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "username or password is incorrect"));
        }
        return ResponseEntity.ok(new AuthResponse(jwt.generateToken(user.getUsername()),user.getUsername()));
    }
}