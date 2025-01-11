package org.example.sheltercare.service;

public interface IServiceSpecies {

    public Species saveSpecies(Species species);
    public List<Species> getAllSpecies();
    public void deleteSpecies(Long id);
}
