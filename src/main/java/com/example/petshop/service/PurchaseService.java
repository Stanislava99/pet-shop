package com.example.petshop.service;

import com.example.petshop.entity.HistoryLog;
import com.example.petshop.entity.Pet;
import com.example.petshop.entity.User;
import com.example.petshop.repo.PetRepository;
import com.example.petshop.repo.PurchaseRepository;
import com.example.petshop.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    @Transactional
    public void buyPets() {
        List<User> users = userRepository.findAll();
        List<Pet> pets = petRepository.findAll();

        int successfulPurchases = 0;
        int unsuccessfulPurchases = 0;

        if (users.isEmpty()) {
            System.out.println("No users");
        }

        if (pets.isEmpty()) {
            System.out.println("No pets");
        }

        for (User user : users) {
            for (Pet pet : pets) {
                if (user.getBudget() >= pet.getPrice() && pet.getOwner() == null) {
                    pet.setOwner(user);
                    petRepository.save(pet);
                    user.setBudget(user.getBudget() - pet.getPrice());
                    userRepository.save(user);
                    switch (pet.getType()) {
                        case CAT ->
                                System.out.println("Meow, cat " + pet.getName() + " has owner " + user.getFullName());
                        case DOG ->
                                System.out.println("Woof, dog " + pet.getName() + " has owner " + user.getFullName());
                        default ->
                                System.out.println("Unknown pet " + pet.getName() + " has owner " + user.getFullName());
                    }
                    successfulPurchases++;
                    break;
                } else {
                    unsuccessfulPurchases++;
                }
            }
        }
        createHistoryLog(successfulPurchases, unsuccessfulPurchases);
    }

    public void createHistoryLog(int successfullyBoughtPet, int unsuccessfullyBoughtPet) {
        HistoryLog historyLog = new HistoryLog();
        historyLog.setExecutionDate(LocalDateTime.now());
        historyLog.setSuccessfullyBoughtPets(successfullyBoughtPet);
        historyLog.setUnsuccessfullyBoughtPet(unsuccessfullyBoughtPet);
        purchaseRepository.save(historyLog);
    }

    public List<HistoryLog> getAllHistoryLogs() {
        return purchaseRepository.findAll();
    }

    public String printHistoryLog() {
        List<HistoryLog> logs = getAllHistoryLogs();
        StringBuilder logHistory = new StringBuilder();

        logHistory.append(String.format("%10s | %20s | %20s%n", "DATE", "SUCCESSFUL PURCHASES", "UNSUCCESSFUL PURCHASES"));
        for (HistoryLog log : logs) {
            LocalDateTime dateTime = LocalDateTime.parse(log.getExecutionDate().toString());
            String dateString = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            logHistory.append(String.format("%10s | %20d | %20d%n", dateString, log.getSuccessfullyBoughtPets(), log.getUnsuccessfullyBoughtPet()));
        }
        return logHistory.toString();
    }
}