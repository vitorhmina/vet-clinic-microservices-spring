package com.vet_clinic.pet_service.controller;

import com.vet_clinic.pet_service.dto.PetRequest;
import com.vet_clinic.pet_service.dto.PetResponse;
import com.vet_clinic.pet_service.service.PetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pet")
@RequiredArgsConstructor
@Slf4j
public class PetController {

    private final PetService petService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetResponse createPet(@RequestBody PetRequest petRequest) {
        log.info("Received request to create a new pet: {}", petRequest);
        PetResponse petResponse = petService.createPet(petRequest);
        log.info("Successfully created pet with ID: {}", petResponse.id());
        return petResponse;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PetResponse getPetById(@PathVariable String id) {
        log.info("Received request to get pet with ID: {}", id);
        PetResponse petResponse = petService.getPetById(id);
        log.info("Successfully fetched pet with ID: {}", id);
        return petResponse;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PetResponse> getAllPets() {
        log.info("Received request to get all pets");
        List<PetResponse> petResponses = petService.getAllPets();
        log.info("Successfully fetched {} pets", petResponses.size());
        return petResponses;
    }

    @GetMapping("/owner/{ownerId}")
    @ResponseStatus(HttpStatus.OK)
    public List<PetResponse> getPetsByOwnerId(@PathVariable String ownerId) {
        log.info("Received request to get all pets for owner with ID: {}", ownerId);
        List<PetResponse> petResponses = petService.getPetsByOwnerId(ownerId);
        log.info("Successfully fetched {} pets for owner with ID: {}", petResponses.size(), ownerId);
        return petResponses;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PetResponse updatePet(@PathVariable String id, @RequestBody PetRequest petRequest) {
        log.info("Received request to update pet with ID: {}", id);
        PetResponse petResponse = petService.updatePet(id, petRequest);
        log.info("Successfully updated pet with ID: {}", id);
        return petResponse;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePet(@PathVariable String id) {
        log.info("Received request to delete pet with ID: {}", id);
        petService.deletePet(id);
        log.info("Successfully deleted pet with ID: {}", id);
    }
}
