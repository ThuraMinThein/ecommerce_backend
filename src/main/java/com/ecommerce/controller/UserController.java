package com.ecommerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.UserDto;
import com.ecommerce.dto.UserWithTokenDto;
import com.ecommerce.model.User;
import com.ecommerce.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserWithTokenDto> createUser(@RequestBody User user) {
        UserWithTokenDto newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserWithTokenDto> login(@RequestBody User user) {
        UserWithTokenDto loginUser =  userService.verify(user);
        return new ResponseEntity<>(loginUser, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers(
        @RequestParam(required = false, name = "search") String search,
        @RequestParam(required = false, name = "role") String role
    ) {
            List<UserDto> users = userService.findAll(search, role);
            return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    

}
