package com.vet_clinic.medical_record_service.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI medicalRecordServiceAPI() {
        return new OpenAPI()
                .info(new Info().title("Medical Record Service API")
                        .description("This is the REST API for Medical Record Service")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("You can refer to the Medical Record Service Wiki Documentation")
                        .url("https://medical-record-service-dummy-url.com/docs"));
    }
}
