package com.vet_clinic.pet_service.service;

import com.vet_clinic.pet_service.exceptions.PetNotFoundException;
import com.vet_clinic.pet_service.model.Pet;
import com.vet_clinic.pet_service.repository.PetRepository;
import com.vet_clinic.pet_service.dto.PetRequest;
import com.vet_clinic.pet_service.dto.PetResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PetService {

    private final PetRepository petRepository;

    public PetResponse createPet(PetRequest petRequest) {
        Pet pet = Pet.builder()
                .name(petRequest.name())
                .species(petRequest.species())
                .age(petRequest.age())
                .breed(petRequest.breed())
                .gender(petRequest.gender())
                .ownerId(petRequest.ownerId())
                .dateOfBirth(petRequest.dateOfBirth())
                .color(petRequest.color())
                .registeredAt(LocalDateTime.now())
                .build();

        petRepository.save(pet);
        log.info("Pet created successfully: {}", pet.getName());
        return mapToPetResponse(pet);
    }

    public PetResponse getPetById(String id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException("Pet not found with ID: " + id));
        return mapToPetResponse(pet);
    }

    public List<PetResponse> getAllPets() {
        return petRepository.findAll()
                .stream()
                .map(this::mapToPetResponse)
                .collect(Collectors.toList());
    }

    public List<PetResponse> getPetsByOwnerId(String ownerId) {
        List<Pet> pets = petRepository.findByOwnerId(ownerId);
        return pets.stream()
                .map(this::mapToPetResponse)
                .collect(Collectors.toList());
    }

    public PetResponse updatePet(String id, PetRequest petRequest) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException("Pet not found with ID: " + id));

        pet.setName(petRequest.name());
        pet.setSpecies(petRequest.species());
        pet.setAge(petRequest.age());
        pet.setBreed(petRequest.breed());
        pet.setGender(petRequest.gender());
        pet.setOwnerId(petRequest.ownerId());
        pet.setDateOfBirth(petRequest.dateOfBirth());
        pet.setColor(petRequest.color());

        petRepository.save(pet);
        log.info("Pet updated successfully: {}", pet.getName());
        return mapToPetResponse(pet);
    }

    public void deletePet(String id) {
        if (!petRepository.existsById(id)) {
            throw new PetNotFoundException("Pet not found with ID: " + id);
        }
        petRepository.deleteById(id);
        log.info("Pet deleted successfully with ID: {}", id);
    }

    private PetResponse mapToPetResponse(Pet pet) {
        return PetResponse.builder()
                .id(pet.getId())
                .name(pet.getName())
                .species(pet.getSpecies())
                .age(pet.getAge())
                .breed(pet.getBreed())
                .gender(pet.getGender())
                .ownerId(pet.getOwnerId())
                .dateOfBirth(pet.getDateOfBirth())
                .color(pet.getColor())
                .registeredAt(pet.getRegisteredAt())
                .build();
    }
}