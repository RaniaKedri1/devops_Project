package org.example.sheltercare.service;

import org.example.sheltercare.entities.MedicalRecord;
import org.springframework.stereotype.Service;

import java.util.List;
public interface IMedicalRecordService {
    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord);
    public List<MedicalRecord> getAllMedicalRecords();
    public MedicalRecord getMedicalRecord(Long id);
    public MedicalRecord editMedicalRecord(Long id, MedicalRecord updatedMedicalRecord);
}
