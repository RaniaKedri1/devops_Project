package org.example.sheltercare.service;

import org.example.sheltercare.entities.MedicalRecord;

import java.util.List;

public interface MedicalRecordService {
    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord);
    public List<MedicalRecord> getAllMedicalRecords();
    public void deleteMedicalRecord(Long id);
}
