package org.example.sheltercare.service;

import org.example.sheltercare.entities.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAnimalService {
    public Animal saveAnimal(Animal animal);
    public List<Animal> getAllAnimals();
    public Animal getAnimal(Long id);
    public Animal updateAnimal(Long id, Animal animal);
    public void deleteAnimal(Long id);
    public Page<Animal> getAnimalByMC(String mc, Pageable page);

}
