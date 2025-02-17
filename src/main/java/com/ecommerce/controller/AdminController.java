package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.service.UserService;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private UserService userService;

    @PostMapping("/seed")
    public String createAdmin() {
        return userService.seedAdmin();
    }

}
