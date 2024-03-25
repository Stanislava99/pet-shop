package com.example.petshop.service;

import com.example.petshop.entity.User;
import com.example.petshop.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> createUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(createUser());
        }
        userRepository.saveAll(users);
        return users;
    }

    private User createUser() {
        String firstName = "User" + new Random().nextInt(1000);
        String lastName = "Last" + new Random().nextInt(1000);
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@example.com";
        double budget = new BigDecimal(new Random().nextDouble() * 30)
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue();

        User user = new User(null, firstName, lastName, email, budget);
        return user;
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }
}
