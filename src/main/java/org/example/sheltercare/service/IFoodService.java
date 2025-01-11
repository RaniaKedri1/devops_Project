package org.example.sheltercare.service;

import org.example.sheltercare.entities.Food;

import java.util.List;

public interface IFoodService {
    public Food saveFood(Food food);

    public List<Food> getAllFoods();

    public Food getFoodById(Long id);

    public void deleteFood(Long id);
}