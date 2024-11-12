package com.vet_clinic.appointment_service.controller;

import com.vet_clinic.appointment_service.dto.AppointmentRequest;
import com.vet_clinic.appointment_service.dto.AppointmentResponse;
import com.vet_clinic.appointment_service.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(@RequestBody AppointmentRequest request) {
        AppointmentResponse appointmentResponse = appointmentService.createAppointment(request);
        return new ResponseEntity<>(appointmentResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable String id) {
        AppointmentResponse appointmentResponse = appointmentService.getAppointmentById(id);
        return new ResponseEntity<>(appointmentResponse, HttpStatus.OK);
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsByPet(@PathVariable String petId) {
        List<AppointmentResponse> appointmentResponses = appointmentService.getAppointmentsByPet(petId);
        return new ResponseEntity<>(appointmentResponses, HttpStatus.OK);
    }

    @GetMapping("/vet/{vetId}/week")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsByVetAndWeek(
            @PathVariable String vetId, @RequestParam("referenceDate") String referenceDateStr) {

        LocalDate referenceDate = LocalDate.parse(referenceDateStr);
        List<AppointmentResponse> appointmentResponses = appointmentService.getAppointmentsByVetAndWeek(vetId, referenceDate);
        return new ResponseEntity<>(appointmentResponses, HttpStatus.OK);
    }

    @PutMapping("/{id}/reschedule")
    public ResponseEntity<AppointmentResponse> rescheduleAppointment(
            @PathVariable String id, @RequestParam("newTime") String newTime) {
        LocalDateTime newAppointmentTime = LocalDateTime.parse(newTime);
        AppointmentResponse appointmentResponse = appointmentService.rescheduleAppointment(id, newAppointmentTime);
        return new ResponseEntity<>(appointmentResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<AppointmentResponse> cancelAppointment(@PathVariable String id) {
        AppointmentResponse appointmentResponse = appointmentService.cancelAppointment(id);
        return new ResponseEntity<>(appointmentResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<AppointmentResponse> completeAppointment(@PathVariable String id) {
        AppointmentResponse appointmentResponse = appointmentService.completeAppointment(id);
        return new ResponseEntity<>(appointmentResponse, HttpStatus.OK);
    }
}