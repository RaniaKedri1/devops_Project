package org.example.sheltercare.service;

import lombok.AllArgsConstructor;
import org.example.sheltercare.entities.Species;
import org.example.sheltercare.repository.SpeciesRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
public class SpeciesServiceImpl implements ISpeciesService{

    private SpeciesRepo speciesRepo;

    @Override
    public Species saveSpecies(Species species) {
        return speciesRepo.save(species);
    }

    @Override
    public List<Species> getAllSpecies() {
        return speciesRepo.findAll();
    }
    @Override
    public void deleteSpecies(Long id) {
        speciesRepo.deleteById(id);
    }
    // Implement the getSpeciesById method
    @Override
    public Optional<Species> getSpeciesById(Long id) {
        return speciesRepo.findById(id);  // Return the species if found, otherwise return an empty Optional
    }
}
