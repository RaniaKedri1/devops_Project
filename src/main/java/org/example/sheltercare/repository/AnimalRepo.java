package org.example.sheltercare.repository;

import org.example.sheltercare.entities.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepo extends JpaRepository<Animal, Long> {

    void deleteById(Long id);
    @Query("select a from Animal a where a.name like %:x%")
    public Page<Animal> findByNomContains(@Param("x")String mc, Pageable p);

    }
