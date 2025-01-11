package org.example.sheltercare.service;

import org.example.sheltercare.entities.Food;

import java.util.List;

public interface IVolunteerService {
    public Food saveVolunteer(Food volunteer);
    public List<Food> getAllVolunteers();
    public void deleteVolunteer(Long id);
}
