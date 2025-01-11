package org.example.sheltercare.controllers;

import org.example.sheltercare.entities.Animal;
import org.example.sheltercare.service.IAnimalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final IAnimalService animalService;

    // Constructor-based dependency injection
    public HomeController(IAnimalService animalService) {
        this.animalService = animalService;
    }
    @GetMapping("/")
    public String showHomePage(Model model) {
        List<Animal> allAnimals = animalService.getAllAnimals();
        System.out.println("Animals: " + allAnimals);  // Logs the animals list
        model.addAttribute("animals", allAnimals);
        return "animals";
    }

}
