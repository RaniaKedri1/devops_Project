package org.example.sheltercare.service;

import lombok.AllArgsConstructor;
import org.example.sheltercare.entities.Food;
import org.example.sheltercare.repository.FoodRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class VolunteerServiceImpl implements IFoodService {
    private FoodRepo volunteerRepo;
    @Override
    public Food saveVolunteer(Food volunteer) {
        return volunteerRepo.save(volunteer);
    }

    @Override
    public List<Food> getAllVolunteers() {
        return volunteerRepo.findAll();
    }

    @Override
    public void deleteVolunteer(Long id) {
        volunteerRepo.deleteById(id);
    }
}
