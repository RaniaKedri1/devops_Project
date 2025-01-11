package org.example.sheltercare.controllers;

import org.example.sheltercare.entities.Animal;
import org.example.sheltercare.service.IAnimalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private IAnimalService animalService;

    @GetMapping("/")
    public String showHomePage(Model model) {
        List<Animal> allAnimals = animalService.getAllAnimals();
        model.addAttribute("animals", allAnimals);  // Pass animals to the view
        return "animals";   // Return to the home page template (Animals.html)
    }
}
