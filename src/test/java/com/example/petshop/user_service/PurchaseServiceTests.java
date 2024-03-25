package com.example.petshop.user_service;

import com.example.petshop.entity.HistoryLog;
import com.example.petshop.entity.Pet;
import com.example.petshop.entity.User;
import com.example.petshop.entity.helper.PetType;
import com.example.petshop.repo.PetRepository;
import com.example.petshop.repo.PurchaseRepository;
import com.example.petshop.repo.UserRepository;
import com.example.petshop.service.PurchaseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTests {

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PurchaseService purchaseService;

    @Test
    void testBuyPets_WithAvailableBudget_SuccessfulPurchase() {
        // GIVEN
        User user = new User(1L, "John", "Doe", "john.doe@example.com", 100.0);
        Pet pet = new Pet("Fluffy", PetType.CAT, "Description", LocalDate.now().minusYears(5), 5);
        pet.setPrice(20.0);

        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        when(petRepository.findAll()).thenReturn(Collections.singletonList(pet));

        // WHEN
        purchaseService.buyPets();

        // THEN
        assertEquals(user, pet.getOwner());
        assertEquals(80.0, user.getBudget());
        verify(purchaseRepository, times(1)).save(any(HistoryLog.class));
    }

    @Test
    void testBuyPets_WithInsufficientBudget_UnsuccessfulPurchase() {
        // GIVEN
        User user = new User(1L, "Jane", "Smith", "jane.smith@example.com", 10.0);
        Pet pet = new Pet("Rex", PetType.DOG, "Description", LocalDate.now().minusYears(10));
        pet.setPrice(20.0);
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        when(petRepository.findAll()).thenReturn(Collections.singletonList(pet));

        // WHEN
        purchaseService.buyPets();

        // THEN
        assertEquals(null, pet.getOwner());
        assertEquals(10.0, user.getBudget());
        verify(purchaseRepository, times(1)).save(any(HistoryLog.class));
    }
}
