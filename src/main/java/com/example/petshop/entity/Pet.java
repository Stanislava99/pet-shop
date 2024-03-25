package com.example.petshop.entity;

import com.example.petshop.entity.helper.PetType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Getter
@Setter
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    @NonNull
    private String name;
    @NonNull
    @Enumerated(EnumType.STRING)
    private PetType type;
    private String description;
    @NonNull
    private LocalDate dateOfBirth;
    private Double price;
    private Integer rating;

    //  Constructor for CAT
    public Pet(String name, PetType type, String description, LocalDate dateOfBirth) {
        this(name, type, description, dateOfBirth, null);
    }

    //  Constructor for DOG
    public Pet(String name, PetType type, String description, LocalDate dateOfBirth, Integer rating) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.dateOfBirth = dateOfBirth;
        this.rating = rating;
    }

    public Pet() {
    }

    @PrePersist
    @PreUpdate
    private void updatePrice() {
        this.price = calculatePrice(type, dateOfBirth, rating);
    }

    private Double calculatePrice(PetType type, LocalDate date, Integer rating) {
        int years = Period.between(date, LocalDate.now()).getYears();
        if (type == PetType.CAT) {
            return years * 1.0; // Price of Cats: age(years) * 1$
        } else {
            return years * 1.0 + this.rating * 1.0; // Price of Dogs: (age(years) * 1$) + (1$ * rating)
        }
    }
}

