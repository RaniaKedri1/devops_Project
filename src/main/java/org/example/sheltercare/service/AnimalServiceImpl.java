package org.example.sheltercare.service;

import lombok.AllArgsConstructor;
import org.example.sheltercare.entities.Animal;
import org.example.sheltercare.repository.AnimalRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AnimalServiceImpl  implements IAnimalService {
    private AnimalRepo animalRepo;
    @Override
    public Animal saveAnimal(Animal animal) {
        return animalRepo.save(animal);
    }

    @Override
    public List<Animal> getAllAnimals() {
        return animalRepo.findAll();
    }

    @Override
    public Animal getAnimal(Long id) {
        return animalRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Animal with ID " + id + " not found"));
    }

//    @Override
//    public Animal updateAnimal(Long id, Animal animal) {
//
//        return animalRepo.save(animal);
//    }
    @Override
    public Animal updateAnimal(Long id, Animal animal) {
        Animal existingAnimal = animalRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Animal with ID " + id + " not found"));
        existingAnimal.setName(animal.getName());  // Replace with actual fields
        existingAnimal.setType(animal.getType()); // Replace with actual fields
        // Update other fields if necessary
        return animalRepo.save(existingAnimal);
    }

    @Override
    public void deleteAnimal(Long id) {
        if (!animalRepo.existsById(id)) {
            throw new IllegalArgumentException("Animal with ID " + id + " does not exist");
        }
        animalRepo.deleteById(id);
    }

    @Override
    public Page<Animal> getAnimalByMC(String mc, Pageable page) {
        return animalRepo.findByNomContains(mc,page); // Uses repository to find by matching 'nom'
    }


}
