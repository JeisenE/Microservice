package com.appfuxion.microservices.controller;

import com.appfuxion.microservices.entity.User;
import com.appfuxion.microservices.repository.UserRepository;
import com.appfuxion.microservices.security.JwtUtil;
import com.appfuxion.microservices.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired private UserService userService;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User payload) {
        if (payload == null || payload.getPassword() == null ||
            payload.getUsername() == null || payload.getEmail() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing required fields"));
        }
        User created = userService.register(payload);
        created.setPassword(null);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> body) {
        String identifier = body.get("identifier");
        String password = body.get("password");
        Optional<User> option = userRepository.findByUsername(identifier);
        if (option.isEmpty()) {
            option = userRepository.findByEmail(identifier);
        }
        if (option.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }
        User user = option.get();
        if (!userService.checkPassword(password, user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }
        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(Map.of("token", token));
    }

}
