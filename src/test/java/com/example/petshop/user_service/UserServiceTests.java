package com.example.petshop.user_service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import com.example.petshop.service.HistoryLogService;
import com.example.petshop.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.petshop.entity.Pet;
import com.example.petshop.entity.User;
import com.example.petshop.entity.helper.PetType;
import com.example.petshop.repo.PetRepository;
import com.example.petshop.repo.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private HistoryLogService historyLogService;

    @InjectMocks
    private UserService userService;

    @Test
    void testBuyPets_WithAvailableBudget_SuccessfulPurchase() {
        // GIVEN
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setBudget(100.0);

        Pet cat = new Pet();
        cat.setId(1L);
        cat.setName("Fluffy");
        cat.setType(PetType.CAT);
        cat.setPrice(20.0);

        List<User> users = Collections.singletonList(user);
        List<Pet> pets = Collections.singletonList(cat);

        when(userRepository.findAll()).thenReturn(users);
        when(petRepository.findAll()).thenReturn(pets);

        // WHEN
        String result = userService.buyPets();

        // THEN
        assertEquals("Meow, cat Fluffy has owner John Doe\n", result);
        assertEquals(80.0, user.getBudget());
        assertEquals(user, cat.getOwner());
        verify(historyLogService, times(1)).createHistoryLog(1, 0);
    }

    @Test
    void testBuyPets_WithInsufficientBudget_UnsuccessfulPurchase() {
        // GIVEN
        User user = new User();
        user.setId(1L);
        user.setFirstName("Jane");
        user.setLastName("Smith");
        user.setEmail("jane.smith@example.com");
        user.setBudget(10.0); // Insufficient budget

        Pet dog = new Pet();
        dog.setId(1L);
        dog.setName("Rex");
        dog.setType(PetType.DOG);
        dog.setPrice(100.0);
        dog.setRating(1); // Expensive pet

        List<User> users = Collections.singletonList(user);
        List<Pet> pets = Collections.singletonList(dog);

        when(userRepository.findAll()).thenReturn(users);
        when(petRepository.findAll()).thenReturn(pets);

        // WHEN
        String result = userService.buyPets();

        // THEN
        assertEquals("Not enough money", result);
        assertEquals(10.0, user.getBudget()); // Budget remains unchanged
        assertEquals(null, dog.getOwner()); // Pet remains unowned
        verify(historyLogService, times(1)).createHistoryLog(0, 1);
    }

    @Test
    void testBuyPets_WithOwner_UnsuccessfulPurchase() {
        // GIVEN

        // GIVEN
        User user = new User();
        user.setId(1L);
        user.setFirstName("Jane");
        user.setLastName("Smith");
        user.setEmail("jane.smith@example.com");
        user.setBudget(120.0);

        User owner = new User();
        owner.setId(1L);
        owner.setFirstName("John");
        owner.setLastName("Smith");
        owner.setEmail("john.smith@example.com");
        owner.setBudget(10.0);

        Pet dog = new Pet();
        dog.setId(1L);
        dog.setOwner(owner); // Pet has owner
        dog.setName("Rex");
        dog.setType(PetType.DOG);
        dog.setPrice(100.0);
        dog.setRating(1); // Expensive pet

        List<User> users = Collections.singletonList(user);
        List<Pet> pets = Collections.singletonList(dog);

        when(userRepository.findAll()).thenReturn(users);
        when(petRepository.findAll()).thenReturn(pets);

        // WHEN
        String result = userService.buyPets();

        // THEN
        assertEquals("Pet has owner", result);
        assertEquals(120.0, user.getBudget()); // Budget remains unchanged
        assertEquals(owner, dog.getOwner()); // Pet remains owned
        verify(historyLogService, times(1)).createHistoryLog(0, 1);
    }
}