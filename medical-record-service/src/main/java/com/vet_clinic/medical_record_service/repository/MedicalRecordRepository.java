package com.vet_clinic.medical_record_service.repository;

import com.vet_clinic.medical_record_service.model.MedicalRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MedicalRecordRepository extends MongoRepository<MedicalRecord, String> {
    List<MedicalRecord> findByPetId(String petId);
}