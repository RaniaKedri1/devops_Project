package org.example.sheltercare.service;

import org.example.sheltercare.entities.Species;

import java.util.List;
import java.util.Optional;

public interface ISpeciesService {

    public Species saveSpecies(Species species);
    public List<Species> getAllSpecies();
    public void deleteSpecies(Long id);
    Optional<Species> getSpeciesById(Long id);
}
