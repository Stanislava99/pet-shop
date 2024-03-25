package com.example.petshop.controller;

import com.example.petshop.entity.User;
import com.example.petshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public List<User> createUsers() {
        return userService.createUsers();
    }

    @GetMapping("")
    public List<User> getUsers() {
        return userService.listUsers();
    }
}