package com.vet_clinic.medical_record_service.service;

import com.vet_clinic.medical_record_service.dto.MedicalRecordRequest;
import com.vet_clinic.medical_record_service.dto.MedicalRecordResponse;
import com.vet_clinic.medical_record_service.exceptions.MedicalRecordNotFoundException;
import com.vet_clinic.medical_record_service.model.MedicalRecord;
import com.vet_clinic.medical_record_service.repository.MedicalRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    @Transactional
    public MedicalRecordResponse createMedicalRecord(MedicalRecordRequest request) {
        log.info("Creating a new medical record for petId: {}", request.petId());

        MedicalRecord medicalRecord = MedicalRecord.builder()
                .petId(request.petId())
                .diagnosis(request.diagnosis())
                .treatment(request.treatment())
                .prescribedMedications(request.prescribedMedications())
                .treatmentDate(request.treatmentDate())
                .vetId(request.vetId())
                .notes(request.notes())
                .isEmergency(request.isEmergency())
                .build();

        medicalRecordRepository.save(medicalRecord);
        log.info("Medical record created successfully with id: {}", medicalRecord.getId());

        return mapToMedicalRecordResponse(medicalRecord);
    }

    public MedicalRecordResponse getMedicalRecordById(String id) {
        log.info("Fetching medical record with id: {}", id);
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new MedicalRecordNotFoundException("Medical record with id: " + id + " not found"));

        log.info("Medical record with id: {} fetched successfully", id);
        return mapToMedicalRecordResponse(medicalRecord);
    }

    public List<MedicalRecordResponse> getMedicalRecordsByPet(String petId) {
        log.info("Fetching medical records for petId: {}", petId);

        List<MedicalRecord> records = medicalRecordRepository.findByPetId(petId);

        return records.stream()
                .map(this::mapToMedicalRecordResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public MedicalRecordResponse updateMedicalRecord(String id, MedicalRecordRequest request) {
        log.info("Updating medical record with id: {}", id);

        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new MedicalRecordNotFoundException("Medical record with id: " + id + " not found"));

        medicalRecord.setDiagnosis(request.diagnosis());
        medicalRecord.setTreatment(request.treatment());
        medicalRecord.setPrescribedMedications(request.prescribedMedications());
        medicalRecord.setTreatmentDate(request.treatmentDate());
        medicalRecord.setNotes(request.notes());
        medicalRecord.setEmergency(request.isEmergency());

        medicalRecordRepository.save(medicalRecord);
        log.info("Medical record with id: {} updated successfully", id);

        return mapToMedicalRecordResponse(medicalRecord);
    }

    @Transactional
    public void deleteMedicalRecord(String id) {
        log.info("Deleting medical record with id: {}", id);

        if (!medicalRecordRepository.existsById(id)) {
            throw new MedicalRecordNotFoundException("Medical record with id: " + id + " not found");
        }

        medicalRecordRepository.deleteById(id);
        log.info("Medical record with id: {} deleted successfully", id);
    }

    private MedicalRecordResponse mapToMedicalRecordResponse(MedicalRecord medicalRecord) {
        return MedicalRecordResponse.builder()
                .id(medicalRecord.getId())
                .petId(medicalRecord.getPetId())
                .diagnosis(medicalRecord.getDiagnosis())
                .treatment(medicalRecord.getTreatment())
                .prescribedMedications(medicalRecord.getPrescribedMedications())
                .treatmentDate(medicalRecord.getTreatmentDate())
                .vetId(medicalRecord.getVetId())
                .notes(medicalRecord.getNotes())
                .isEmergency(medicalRecord.isEmergency())
                .build();
    }
}
