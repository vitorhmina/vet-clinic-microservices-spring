package com.vet_clinic.appointment_service.dto;

import java.time.LocalDateTime;

public record AppointmentRequest(
        String id,
        String petId,
        String vetId,
        LocalDateTime appointmentTime,
        String status,
        String reason
) {}
