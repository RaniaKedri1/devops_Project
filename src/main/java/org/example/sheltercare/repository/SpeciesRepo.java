package org.example.sheltercare.repository;

import org.example.sheltercare.entities.Species;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpeciesRepo extends JpaRepository<Species,Long> {
   List<Species> findAll();

}
