package com.osm.treasure_hunting.controllers;

import com.osm.treasure_hunting.DTO.RegisterDTO;
import com.osm.treasure_hunting.DTO.UserDTO;
import com.osm.treasure_hunting.models.User;
import com.osm.treasure_hunting.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public UserDTO register(@RequestBody RegisterDTO registerDTO) {
        User user = userService.createUser(registerDTO.getUsername());
        return UserDTO.builder().username(user.getUsername()).id(user.getId()).build();
    }
}
