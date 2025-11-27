package com.appfuxion.microservices.controller;

import com.appfuxion.microservices.entity.User;
import com.appfuxion.microservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired private UserService userService;
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

    @GetMapping("/me") 
    public ResponseEntity<?> me(Authentication authentication) {
        if (authentication == null) return ResponseEntity.status(401).build();

        String email = authentication.getName();
        var byEmail = userService.findByEmail(email);

        return byEmail.map(u -> {u.setPassword(null);
            return ResponseEntity.ok(u);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfile(
        @PathVariable Long id,
        @RequestBody User updated,
        Authentication authentication) {
        String requestEmail = authentication.getName();
        Long requestId = userService.findByEmail(requestEmail).map(User::getId).orElseThrow();
        if (!requestId.equals(id)) {
            return ResponseEntity.status(403).body(Map.of("error", "Forbidden"));
        }
        try {
            User saved = userService.updateProfile(id, updated);
            saved.setPassword(null);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).body(Map.of("error", ex.getMessage()));
        }
    }

}
