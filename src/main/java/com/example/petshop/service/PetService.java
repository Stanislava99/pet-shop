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
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            Pet pet = new Pet();
            pet.setName("Pet" + i);
            PetType randomType = random.nextBoolean() ? PetType.CAT : PetType.DOG;
            pet.setType(randomType);
            pet.setDescription("Description for Pet" + i);
            pet.setDate(LocalDate.now().minusDays(random.nextInt(365 * 5))); // Random date within the last 5 years
            if (randomType == PetType.DOG) {
                pet.setRating(random.nextInt(11)); // Random rating between 0 and 10
            }
            petRepository.save(pet);
            pets.add(pet);
        }
        return pets;
    }

    public List<Pet> listPets() {
        return petRepository.findAll();
    }
}
