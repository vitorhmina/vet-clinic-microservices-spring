package com.vet_clinic.appointment_service.repository;

import com.vet_clinic.appointment_service.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {
    boolean existsByVetIdAndAppointmentTimeBetween(String vetId, LocalDateTime startTime, LocalDateTime endTime);

    List<Appointment> findByPetId(String petId);

    List<Appointment> findByVetIdAndAppointmentTimeBetween(String vetId, LocalDateTime startOfWeekDateTime, LocalDateTime endOfWeekDateTime);
}
