package org.example.sheltercare.service;

import lombok.AllArgsConstructor;
import org.example.sheltercare.entities.Food;
import org.example.sheltercare.repository.FoodRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class FoodServiceImpl implements IFoodService {
    private FoodRepo foodRepo;

    @Override
    public Food saveFood(Food food) {
        return foodRepo.save(food);
    }

    @Override
    public List<Food> getAllFoods() {
        return foodRepo.findAll();
    }

    @Override
    public Food getFoodById(Long id) {
        return foodRepo.findById(id).orElseThrow(() -> new RuntimeException("Food not found"));
    }

    @Override
    public void deleteFood(Long id) {
        foodRepo.deleteById(id);
    }
}