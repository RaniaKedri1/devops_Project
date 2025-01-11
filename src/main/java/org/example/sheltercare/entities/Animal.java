package org.example.sheltercare.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Animal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String Breed;
    private String gender;
    private int age;
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public enum AnimalStatus {
        AVAILABLE("text-success"),
        ADOPTED("text-danger"),
        IN_CARE("text-secondary");

        private final String color;

        // Constructor to initialize the color
        AnimalStatus(String color) {
            this.color = color;
        }

        // Getter to retrieve the color
        public String getColor() {
            return color;
        }
    }

    @Enumerated(EnumType.STRING)
    private AnimalStatus status;

    @OneToOne(mappedBy = "animal",cascade = CascadeType.ALL, orphanRemoval = true)
    private MedicalRecord medicalRecord;

    @ManyToOne
    private Species spe;

    @ManyToMany
    private List<Food> foods = new ArrayList<>();

    public void setSpe(Species spe) {
        this.spe = spe;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

}

