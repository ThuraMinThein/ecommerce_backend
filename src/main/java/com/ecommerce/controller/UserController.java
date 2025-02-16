package com.ecommerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.UserDto;
import com.ecommerce.model.User;
import com.ecommerce.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public UserDto createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public UserDto login(@RequestBody User user) {
        return userService.verify(user);
    }

    @GetMapping()
    public List<UserDto> getAllUsers(
        @RequestParam(required = false, name = "search") String search,
        @RequestParam(required = false, name = "role") String role
    ) {
            return userService.findAll(search, role);
    }
    
    

}
