package com.vet_clinic.pet_service.dto;

import java.time.LocalDate;

public record PetRequest(

        String id,
        String name,
        String species,
        Integer age,
        String breed,
        String gender,
        String ownerId,
        LocalDate dateOfBirth,
        String color
) {
}
