package com.vet_clinic.pet_service;

import org.springframework.boot.SpringApplication;

public class TestPetServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(PetServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
