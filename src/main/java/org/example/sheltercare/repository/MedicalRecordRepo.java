package org.example.sheltercare.repository;

import org.example.sheltercare.entities.Animal;
import org.example.sheltercare.entities.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MedicalRecordRepo extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByAnimalIsNotNull();

    // Method to find animals that do not have a medical record
    @Query("SELECT a FROM Animal a WHERE a NOT IN (SELECT DISTINCT m.animal FROM MedicalRecord m WHERE m.animal IS NOT NULL)")
    List<Animal> findAnimalsWithoutMedicalRecord();
}
