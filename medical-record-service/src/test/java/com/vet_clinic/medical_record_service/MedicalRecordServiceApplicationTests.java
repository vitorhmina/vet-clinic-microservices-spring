package com.vet_clinic.medical_record_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class MedicalRecordServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
