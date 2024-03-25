package com.example.petshop.service;

import com.example.petshop.entity.Pet;
import com.example.petshop.entity.helper.PetType;
import com.example.petshop.repo.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;

    public List<Pet> createPets() {
        List<Pet> pets = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            pets.add(createPet());
        }
        petRepository.saveAll(pets);
        return pets;
    }

    private Pet createPet() {
        String petName = "Pet" + new Random().nextInt(1000);
        PetType petType = new Random().nextBoolean() ? PetType.CAT : PetType.DOG;
        String description = "Description for " + petName;
        LocalDate dateOfBirth = LocalDate.now().minusDays(new Random().nextInt(365 * 7));

        if (petType == PetType.DOG) {
            int rating = new Random().nextInt(10) + 1;
            return new Pet(petName, petType, description, dateOfBirth, rating);
        } else {
            return new Pet(petName, petType, description, dateOfBirth);
        }
    }

    public List<Pet> listPets() {
        return petRepository.findAll();
    }
}
