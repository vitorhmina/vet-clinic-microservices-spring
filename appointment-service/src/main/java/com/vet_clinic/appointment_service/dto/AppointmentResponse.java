package com.vet_clinic.appointment_service.dto;

import com.vet_clinic.appointment_service.model.AppointmentStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AppointmentResponse (
        String id,
        String petId,
        String vetId,
        LocalDateTime appointmentTime,
        AppointmentStatus status,
        String reason,
        LocalDateTime createdAt
) {
}
