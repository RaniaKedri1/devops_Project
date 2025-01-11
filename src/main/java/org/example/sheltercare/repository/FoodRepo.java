package org.example.sheltercare.repository;

import org.example.sheltercare.entities.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
    public interface FoodRepo extends JpaRepository<Food, Long> {
}
