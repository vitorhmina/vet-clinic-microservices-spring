package com.vet_clinic.notification_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentCreatedEvent {
    private String appointmentId;
    private String petId;
    private String vetId;
    private LocalDateTime appointmentTime;
    private String reason;
    private LocalDateTime createdAt;

    private String email;
    private String firstName;
    private String LastName;
}
