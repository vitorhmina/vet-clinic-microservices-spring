package com.vet_clinic.medical_record_service.controller;

import com.vet_clinic.medical_record_service.dto.MedicalRecordRequest;
import com.vet_clinic.medical_record_service.dto.MedicalRecordResponse;
import com.vet_clinic.medical_record_service.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/record")
@RequiredArgsConstructor
@Slf4j
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @PostMapping
    public ResponseEntity<MedicalRecordResponse> createMedicalRecord(@RequestBody MedicalRecordRequest request) {
        log.info("Received request to create a new medical record for petId: {}", request.petId());
        MedicalRecordResponse response = medicalRecordService.createMedicalRecord(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordResponse> getMedicalRecordById(@PathVariable String id) {
        log.info("Received request to get medical record by id: {}", id);
        MedicalRecordResponse response = medicalRecordService.getMedicalRecordById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<MedicalRecordResponse>> getMedicalRecordsByPet(@PathVariable String petId) {
        log.info("Received request to get all medical records for petId: {}", petId);
        List<MedicalRecordResponse> responses = medicalRecordService.getMedicalRecordsByPet(petId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecordResponse> updateMedicalRecord(
            @PathVariable String id,
            @RequestBody MedicalRecordRequest request) {
        log.info("Received request to update medical record with id: {}", id);
        MedicalRecordResponse response = medicalRecordService.updateMedicalRecord(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable String id) {
        log.info("Received request to delete medical record with id: {}", id);
        medicalRecordService.deleteMedicalRecord(id);
        return ResponseEntity.noContent().build();
    }
}
