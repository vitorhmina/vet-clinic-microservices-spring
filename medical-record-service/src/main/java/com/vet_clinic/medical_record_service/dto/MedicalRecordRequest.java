package com.vet_clinic.medical_record_service.dto;

import java.time.LocalDate;
import java.util.List;

public record MedicalRecordRequest(

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
