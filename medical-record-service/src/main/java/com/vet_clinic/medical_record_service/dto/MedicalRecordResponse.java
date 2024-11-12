package com.vet_clinic.medical_record_service.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record MedicalRecordResponse(

        String id,
        String petId,
        String diagnosis,
        String treatment,
        List<String>prescribedMedications,
        LocalDate treatmentDate,
        String vetId,
        String notes,
        boolean isEmergency
) {
}
