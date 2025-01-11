package org.example.sheltercare.controllers;

import org.example.sheltercare.entities.Animal;
import org.example.sheltercare.entities.MedicalRecord;
import org.example.sheltercare.repository.MedicalRecordRepo;
import org.example.sheltercare.service.IAnimalService;
import org.example.sheltercare.service.IMedicalRecordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/medicalrecords")
public class MedicalRecController {
    private final MedicalRecordRepo medicalRecordRepo;
    private final IMedicalRecordService medicalRecordService;
    private final IAnimalService animalService;

    // Manually creating the constructor
    public MedicalRecController(MedicalRecordRepo medicalRecordRepo,
                                IMedicalRecordService medicalRecordService,
                                IAnimalService animalService) {
        this.medicalRecordRepo = medicalRecordRepo;
        this.medicalRecordService = medicalRecordService;
        this.animalService = animalService;
    }

    @GetMapping
    public String listMedicalRecords(Model model) {
        List<MedicalRecord> medicalRecords = medicalRecordService.getAllMedicalRecords();
        model.addAttribute("medicalRecords", medicalRecords);
        return "medicalrecords/list";
    }

    @GetMapping("/add")
    public String showAddMedicalRecordForm(Model model) {
        List<Animal> availableAnimals = medicalRecordRepo.findAnimalsWithoutMedicalRecord();
        model.addAttribute("animals", availableAnimals);
        model.addAttribute("medicalRecord", new MedicalRecord());
        return "medicalrecords/add";
    }

    @PostMapping("/save")
    public String saveMedicalRecord(@ModelAttribute MedicalRecord medicalRecord) {
        medicalRecordService.saveMedicalRecord(medicalRecord);
        return "redirect:/medicalrecords";
    }

    @GetMapping("/edit/{id}")
    public String showEditMedicalRecordForm(@PathVariable Long id, Model model) {
        MedicalRecord medicalRecord = medicalRecordService.getMedicalRecord(id);
        model.addAttribute("medicalRecord", medicalRecord);
        return "medicalrecords/edit";
    }

    @PostMapping("/update/{id}")
    public String updateMedicalRecord(@PathVariable Long id, @ModelAttribute MedicalRecord updatedMedicalRecord) {
        // Fetch the existing medical record first
        MedicalRecord existingMedicalRecord = medicalRecordRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Medical Record not found"));

        // Preserve the existing animal
        updatedMedicalRecord.setAnimal(existingMedicalRecord.getAnimal());
        medicalRecordRepo.save(updatedMedicalRecord);
        return "redirect:/medicalrecords";
    }
}
