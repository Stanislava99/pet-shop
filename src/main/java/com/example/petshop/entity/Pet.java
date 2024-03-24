package com.example.petshop.entity;

import com.example.petshop.entity.helper.PetType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    private String name;
    @Enumerated(EnumType.STRING)
    private PetType type;
    private String description;
    private LocalDate date;
    private Double price;
    private Integer rating;

    @PrePersist
    @PreUpdate
    private void updatePrice() {
        this.price = calculatePrice(type, date, rating);
    }

//  Constructor for CAT
    public Pet(Long id, String name, PetType type, String description, LocalDate date) {
        this(id, name, type, description, date, null);
    }

//  Constructor for DOG
    public Pet(Long id, String name, PetType type, String description, LocalDate date, Integer rating) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.date = date;
        this.rating = rating;
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

