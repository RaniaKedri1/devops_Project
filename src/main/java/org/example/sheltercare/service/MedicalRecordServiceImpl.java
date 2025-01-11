package org.example.sheltercare.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.sheltercare.entities.MedicalRecord;
import org.example.sheltercare.repository.MedicalRecordRepo;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class MedicalRecordServiceImpl implements IMedicalRecordService {

    private MedicalRecordRepo medicalRecordRepo;
    @Override
    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepo.save(medicalRecord);
    }

    @Override
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepo.findAll();
    }


    @Override
    public MedicalRecord getMedicalRecord(Long id) {
        return medicalRecordRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("MedicalRecord with ID " + id + " not found"));
    }

    @Override
    public MedicalRecord editMedicalRecord(Long id, MedicalRecord updatedMedicalRecord) {
        return medicalRecordRepo.save(updatedMedicalRecord);
    }
}
