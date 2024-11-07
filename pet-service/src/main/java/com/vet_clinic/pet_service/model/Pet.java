package com.vet_clinic.pet_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(value = "pet")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pet {

    @Id
    private String id;

    private String name;
    private String species;
    private Integer age;
    private String breed;
    private String gender;
    private String ownerId;
    private LocalDate dateOfBirth;
    private String color;
    private LocalDateTime registeredAt;
}
