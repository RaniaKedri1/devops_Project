package org.example.sheltercare.repository;

import org.example.sheltercare.entities.Food;
import org.springframework.data.jpa.repository.JpaRepository;

    public interface VolunteerRepo extends JpaRepository<Food, Long> {
}
