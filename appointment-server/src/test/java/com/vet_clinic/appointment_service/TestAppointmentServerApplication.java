package com.vet_clinic.appointment_service;

import org.springframework.boot.SpringApplication;

public class TestAppointmentServerApplication {

	public static void main(String[] args) {
		SpringApplication.from(AppointmentServerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
