package com.appfuxion.microservices.service;

import com.appfuxion.microservices.entity.User;
import com.appfuxion.microservices.repository.UserRepository;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User register(User insert){
        if(userRepository.findByUsername(insert.getUsername()).isPresent()){
            throw new RuntimeException("username_exists");
        }
        if(userRepository.findByEmail(insert.getEmail()).isPresent()){
            throw new RuntimeException("email_exists");
        }
        insert.setPassword(passwordEncoder.encode(insert.getPassword()));
        return userRepository.save(insert);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public User updateProfile(Long id, User updated) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("No_Found"));
        user.setName(updated.getName());
        user.setAddress(updated.getAddress());
        user.setAge(updated.getAge());
        if (updated.getEmail() != null && !updated.getEmail().equals(user.getEmail())) {
            if (userRepository.findByEmail(updated.getEmail()).isPresent()) {
                throw new RuntimeException("email_exists");
            }
            user.setEmail(updated.getEmail());
        }
        return userRepository.save(user);
    }

    public boolean checkPassword(String rawPassword, String encoded) {
        return passwordEncoder.matches(rawPassword, encoded);
    }
}
