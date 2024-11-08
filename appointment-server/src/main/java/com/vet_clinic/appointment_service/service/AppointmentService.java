package com.vet_clinic.appointment_service.service;

import com.vet_clinic.appointment_service.event.AppointmentCanceledEvent;
import com.vet_clinic.appointment_service.event.AppointmentCreatedEvent;
import com.vet_clinic.appointment_service.event.AppointmentRescheduledEvent;
import com.vet_clinic.appointment_service.exceptions.AppointmentConflictException;
import com.vet_clinic.appointment_service.exceptions.AppointmentNotFoundException;
import com.vet_clinic.appointment_service.model.Appointment;
import com.vet_clinic.appointment_service.model.AppointmentStatus;
import com.vet_clinic.appointment_service.repository.AppointmentRepository;
import com.vet_clinic.appointment_service.dto.AppointmentRequest;
import com.vet_clinic.appointment_service.dto.AppointmentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final KafkaTemplate<String, AppointmentCreatedEvent> createdEventKafkaTemplate;
    private final KafkaTemplate<String, AppointmentRescheduledEvent> rescheduledEventKafkaTemplate;
    private final KafkaTemplate<String, AppointmentCanceledEvent> canceledEventKafkaTemplate;

    private static final String APPOINTMENT_CREATED_TOPIC = "APPOINTMENT_CREATED";
    private static final String APPOINTMENT_RESCHEDULED_TOPIC = "APPOINTMENT_RESCHEDULED";
    private static final String APPOINTMENT_CANCELED_TOPIC = "APPOINTMENT_CANCELED";

    @Transactional
    public AppointmentResponse createAppointment(AppointmentRequest request) {
        log.info("Creating a new appointment for petId: {} and vetId: {}", request.petId(), request.vetId());

        if (hasConflictingAppointment(request.vetId(), request.appointmentTime())) {
            log.warn("Conflict: Vet {} already has an appointment within 30 minutes of the requested time", request.vetId());
            throw new AppointmentConflictException("This vet has another appointment scheduled within 30 minutes of the requested time");
        }

        Appointment appointment = Appointment.builder()
                .petId(request.petId())
                .vetId(request.vetId())
                .appointmentTime(request.appointmentTime())
                .status(AppointmentStatus.SCHEDULED)
                .reason(request.reason())
                .createdAt(LocalDateTime.now())
                .build();

        appointmentRepository.save(appointment);
        log.info("Appointment created successfully with id: {}", appointment.getId());

        AppointmentCreatedEvent createdEvent = new AppointmentCreatedEvent(
                appointment.getId(),
                appointment.getPetId(),
                appointment.getVetId(),
                appointment.getAppointmentTime(),
                appointment.getReason(),
                appointment.getCreatedAt()
        );
        createdEventKafkaTemplate.send(APPOINTMENT_CREATED_TOPIC, createdEvent);

        return mapToAppointmentResponse(appointment);
    }

    public AppointmentResponse getAppointmentById(String id) {
        log.info("Fetching appointment with id: {}", id);
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with id: " + id + " not found"));

        log.info("Appointment with id: {} fetched successfully", id);
        return mapToAppointmentResponse(appointment);
    }

    public List<AppointmentResponse> getAppointmentsByPet(String petId) {
        List<Appointment> appointments = appointmentRepository.findByPetId(petId);

        return appointments.stream()
                .map(this::mapToAppointmentResponse)
                .collect(Collectors.toList());
    }

    public List<AppointmentResponse> getAppointmentsByVetAndWeek(String vetId, LocalDate referenceDate) {
        LocalDate startOfWeek = referenceDate.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = referenceDate.with(DayOfWeek.SUNDAY);

        LocalDateTime startOfWeekDateTime = startOfWeek.atStartOfDay();
        LocalDateTime endOfWeekDateTime = endOfWeek.atTime(23, 59, 59);

        List<Appointment> appointments = appointmentRepository.findByVetIdAndAppointmentTimeBetween(
                vetId, startOfWeekDateTime, endOfWeekDateTime);

        return appointments.stream()
                .map(this::mapToAppointmentResponse)
                .collect(Collectors.toList());
    }


    @Transactional
    public AppointmentResponse rescheduleAppointment(String id, LocalDateTime newTime) {
        log.info("Rescheduling appointment with id: {}", id);

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with id: " + id + " not found"));

        if (hasConflictingAppointment(appointment.getVetId(), newTime)) {
            log.warn("Conflict: Vet {} has another appointment within 30 minutes of the new requested time", appointment.getVetId());
            throw new AppointmentConflictException("This vet has another appointment scheduled within 30 minutes of the new time");
        }

        LocalDateTime oldTime = appointment.getAppointmentTime();
        appointment.setAppointmentTime(newTime);
        appointment.setStatus(AppointmentStatus.RESCHEDULED);
        appointmentRepository.save(appointment);

        log.info("Appointment with id: {} rescheduled successfully", id);

        AppointmentRescheduledEvent rescheduledEvent = new AppointmentRescheduledEvent(
                appointment.getId(),
                appointment.getPetId(),
                appointment.getVetId(),
                oldTime,
                newTime,
                "Rescheduled by request"
        );
        rescheduledEventKafkaTemplate.send(APPOINTMENT_RESCHEDULED_TOPIC, rescheduledEvent);

        return mapToAppointmentResponse(appointment);
    }

    @Transactional
    public AppointmentResponse cancelAppointment(String id) {
        log.info("Canceling appointment with id: {}", id);

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with id: " + id + " not found"));

        appointment.setStatus(AppointmentStatus.CANCELED);
        appointmentRepository.save(appointment);

        log.info("Appointment with id: {} canceled successfully", id);

        AppointmentCanceledEvent canceledEvent = new AppointmentCanceledEvent(
                appointment.getId(),
                appointment.getPetId(),
                appointment.getVetId(),
                "Canceled by user request",
                LocalDateTime.now()
        );
        canceledEventKafkaTemplate.send(APPOINTMENT_CANCELED_TOPIC, canceledEvent);

        return mapToAppointmentResponse(appointment);
    }

    @Transactional
    public AppointmentResponse completeAppointment(String id) {
        log.info("Completing appointment with id: {}", id);

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with id: " + id + " not found"));

        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);

        log.info("Appointment with id: {} completed successfully", id);

        return mapToAppointmentResponse(appointment);
    }

    private boolean hasConflictingAppointment(String vetId, LocalDateTime requestedTime) {
        LocalDateTime startTime = requestedTime.minusMinutes(30);
        LocalDateTime endTime = requestedTime.plusMinutes(30);

        return appointmentRepository.existsByVetIdAndAppointmentTimeBetween(vetId, startTime, endTime);
    }

    private AppointmentResponse mapToAppointmentResponse(Appointment appointment) {
        return AppointmentResponse.builder()
                .id(appointment.getId())
                .petId(appointment.getPetId())
                .vetId(appointment.getVetId())
                .appointmentTime(appointment.getAppointmentTime())
                .status(appointment.getStatus())
                .reason(appointment.getReason())
                .createdAt(appointment.getCreatedAt())
                .build();
    }
}