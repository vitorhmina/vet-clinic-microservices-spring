package com.vet_clinic.appointment_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRescheduledEvent {
    private String appointmentId;
    private String petId;
    private String vetId;
    private LocalDateTime oldAppointmentTime;
    private LocalDateTime newAppointmentTime;
    private String reasonForReschedule;
}
