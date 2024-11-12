package com.vet_clinic.medical_record_service;

import org.springframework.boot.SpringApplication;

public class TestMedicalRecordServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(MedicalRecordServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
