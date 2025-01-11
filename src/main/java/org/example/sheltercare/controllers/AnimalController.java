import lombok.RequiredArgsConstructor;
import org.example.sheltercare.entities.Animal;
import org.example.sheltercare.entities.Food;
import org.example.sheltercare.entities.Species;
import org.example.sheltercare.repository.AnimalRepo;
import org.example.sheltercare.service.FoodServiceImpl;
import org.example.sheltercare.service.IAnimalService;
import org.example.sheltercare.service.ISpeciesService;
import org.example.sheltercare.service.MedicalRecordServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor // Replacing @AllArgsConstructor with @RequiredArgsConstructor
@RequestMapping("/animals")
public class AnimalController {

    private final FoodServiceImpl foodServiceImpl;
    private final IAnimalService animalService;
    private final AnimalRepo animalRepo;
    private final MedicalRecordServiceImpl medicalRecordService;
    private final ISpeciesService speciesService;

    public AnimalController(FoodServiceImpl foodServiceImpl,
                            IAnimalService animalService,
                            AnimalRepo animalRepo,
                            MedicalRecordServiceImpl medicalRecordService,
                            ISpeciesService speciesService) {
        this.foodServiceImpl = foodServiceImpl;
        this.animalService = animalService;
        this.animalRepo = animalRepo;
        this.medicalRecordService = medicalRecordService;
        this.speciesService = speciesService;
    }

    @GetMapping
    public String getAnimalByMC(
            Model model,
            @RequestParam(name = "mc", defaultValue = "") String mc,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {
        Page<Animal> animals = animalService.getAnimalByMC(mc, PageRequest.of(page - 1, size));
        model.addAttribute("animals", animals.getContent());
        model.addAttribute("currentPage", animals.getNumber() + 1);
        model.addAttribute("totalPages", animals.getTotalPages());
        model.addAttribute("mc", mc);
        return "animals/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteAnimal(@PathVariable Long id) {
        animalService.deleteAnimal(id);
        return "redirect:/animals";
    }

    @GetMapping("/add")
    public String showAddAnimalForm(Model model) {
        model.addAttribute("animal", new Animal());
        model.addAttribute("speciesList", speciesService.getAllSpecies());
        model.addAttribute("foodList", foodServiceImpl.getAllFoods());
        return "animals/add";
    }

    @PostMapping("/save")
    public String saveAnimal(@ModelAttribute Animal animal,
                             @RequestParam("speciesId") Long speciesId,
                             @RequestParam(value = "foodIds", required = false) List<Long> foodIds) {
        // Set species
        Species species = speciesService.getSpeciesById(speciesId)
                .orElseThrow(() -> new RuntimeException("Species not found"));
        animal.setSpe(species);

        // Set selected foods
        if (foodIds != null) {
            List<Food> selectedFoods = foodIds.stream()
                    .map(foodServiceImpl::getFoodById)
                    .toList();
            animal.setFoods(selectedFoods);
        } else {
            animal.setFoods(new ArrayList<>());
        }

        // Save animal
        animalService.saveAnimal(animal);
        return "redirect:/animals";
    }

    @GetMapping("/edit/{id}")
    public String showEditAnimalForm(@PathVariable Long id, Model model) {
        Animal animal = animalService.getAnimal(id);
        List<Food> foodList = foodServiceImpl.getAllFoods();
        model.addAttribute("animal", animal);
        model.addAttribute("speciesList", speciesService.getAllSpecies());
        model.addAttribute("foodList", foodList);
        return "animals/edit";
    }

    @PostMapping("/update/{id}")
    public String updateAnimal(@PathVariable Long id, @ModelAttribute Animal animal,
                               @RequestParam("speciesId") Long speciesId,
                               @RequestParam(value = "foodIds", required = false) List<Long> foodIds) {
        Species species = speciesService.getSpeciesById(speciesId)
                .orElseThrow(() -> new RuntimeException("Species not found"));
        animal.setSpe(species);

        // Set selected foods
        if (foodIds != null) {
            List<Food> selectedFoods = foodIds.stream()
                    .map(foodServiceImpl::getFoodById)
                    .toList();
            animal.setFoods(selectedFoods);
        } else {
            animal.setFoods(new ArrayList<>());
        }

        animalService.updateAnimal(id, animal);
        return "redirect:/animals";
    }
}
