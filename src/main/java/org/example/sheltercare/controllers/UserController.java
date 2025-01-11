package org.example.sheltercare.controllers;

import lombok.AllArgsConstructor;
import org.example.sheltercare.entities.Animal;
import org.example.sheltercare.service.IAnimalService;
import org.example.sheltercare.service.IFoodService;
import org.example.sheltercare.service.ISpeciesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final IAnimalService animalService;
    private final ISpeciesService speciesService;
    private final IFoodService foodService;

    // Manually defining the constructor
    public UserController(IAnimalService animalService, ISpeciesService speciesService, IFoodService foodService) {
        this.animalService = animalService;
        this.speciesService = speciesService;
        this.foodService = foodService;
    }

    @GetMapping("/")
    public String showHomePage(Model model) {
        List<Animal> allAnimals = animalService.getAllAnimals();
        model.addAttribute("animals", allAnimals);  // Pass animals to the view
        return "index";   // Return to the home page template (index.html)
    }

    @GetMapping("/adopt/{id}")
    public String adoptAnimal(@PathVariable Long id, Model model) {
        Animal animal = animalService.getAnimal(id);  // Fetch animal by ID
        model.addAttribute("animal", animal);  // Pass the animal to the view
        return "animals/adopt";
    }

    // Handle volunteering for an animal
    @GetMapping("/volunteer/{id}")
    public String volunteerForAnimal(@PathVariable Long id, Model model) {
        Animal animal = animalService.getAnimal(id);  // Fetch animal by ID
        model.addAttribute("animal", animal);  // Pass the animal to the view
        return "animals/volunteer";
    }
}
