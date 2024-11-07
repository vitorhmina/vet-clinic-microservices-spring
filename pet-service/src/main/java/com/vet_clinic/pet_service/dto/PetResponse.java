package com.vet_clinic.pet_service.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record PetResponse(

        String id,
        String name,
        String species,
        Integer age,
        String breed,
        String gender,
        String ownerId,
        LocalDate dateOfBirth,
        String color,
        LocalDateTime registeredAt
) {
}
