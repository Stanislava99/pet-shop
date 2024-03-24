package com.example.petshop.service;

import com.example.petshop.entity.Pet;
import com.example.petshop.entity.helper.PetType;
import com.example.petshop.entity.User;
import com.example.petshop.repo.PetRepository;
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
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private HistoryLogService historyLogService;

    public List<User> createUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setFirstName("User" + i);
            user.setLastName("Last" + i);
            user.setEmail("user" + i + "@example.com");
            double randomBudget = new BigDecimal(new Random().nextDouble() * 30)
                    .setScale(1, RoundingMode.HALF_UP)
                    .doubleValue();
            user.setBudget(randomBudget); // Random budget
            userRepository.save(user);
            users.add(user);
        }
        return users;
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public String buyPets() {
        List<User> users = userRepository.findAll();
        List<Pet> pets = petRepository.findAll();

        StringBuilder message = new StringBuilder();

        int successfulPurchases = 0;
        int unsuccessfulPurchases = 0;

        for (User user : users) {
            for (Pet pet : pets) {
                if (user.getBudget() >= pet.getPrice() && pet.getOwner() == null) {
                    pet.setOwner(user);
                    petRepository.save(pet);
                    user.setBudget(user.getBudget() - pet.getPrice());
                    userRepository.save(user);
                    if (pet.getType() == PetType.CAT) {
                        message.append("Meow, cat ").append(pet.getName()).append(" has owner ").append(user.getFirstName()).append(" ").append(user.getLastName()).append("\n");
                    } else {
                        message.append("Woof, dog ").append(pet.getName()).append(" has owner ").append(user.getFirstName()).append(" ").append(user.getLastName()).append("\n");
                    }
                    successfulPurchases++;
                    break;
                } else {
                    unsuccessfulPurchases++;
                    if (user.getBudget() < pet.getPrice()) {
                        message.append("Not enough money");
                    } else {
                        message.append("Pet has owner");
                    }
                }
            }
        }
        if (successfulPurchases == 0 && unsuccessfulPurchases == 0) {
            message.append("No purchases made :(");
        }
        historyLogService.createHistoryLog(successfulPurchases, unsuccessfulPurchases);
        return message.toString();
    }
}
