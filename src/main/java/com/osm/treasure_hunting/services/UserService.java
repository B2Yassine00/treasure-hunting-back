package com.osm.treasure_hunting.services;

import com.osm.treasure_hunting.models.User;
import com.osm.treasure_hunting.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(String username) {
        return userRepository.findByUsername(username)
                .orElseGet(() -> userRepository.save(User.builder().username(username).build()));
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}

