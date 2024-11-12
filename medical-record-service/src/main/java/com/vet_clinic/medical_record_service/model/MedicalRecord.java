package com.vet_clinic.medical_record_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document(value = "medical_record")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalRecord {

    @Id
    private String id;

    private String petId;
    private String diagnosis;
    private String treatment;
    private List<String> prescribedMedications;
    private LocalDate treatmentDate;
    private String vetId;
    private String notes;
    private boolean isEmergency;
}
