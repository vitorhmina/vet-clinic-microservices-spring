package com.hms.api_gateway.routes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;

@Configuration
public class Routes {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Value("${spring.cloud.gateway.routes[2].id}")
    private String petServiceName;

    @Value("${spring.cloud.gateway.routes[3].id}")
    private String appointmentServiceName;

    @Value("${spring.cloud.gateway.routes[4].id}")
    private String medicalRecordServiceName;

    @Bean
    public RouterFunction<ServerResponse> petServiceRoute() {
        String petServiceUrl = loadBalancerClient.choose(petServiceName).getUri().toString();

        return GatewayRouterFunctions.route("pet_service")
                .route(RequestPredicates.path("/api/pet/**"), HandlerFunctions.http(petServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("petServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> petServiceSwaggerRoute() {
        String petServiceUrl = loadBalancerClient.choose(petServiceName).getUri().toString();

        return GatewayRouterFunctions.route("pet_service_swagger")
                .route(RequestPredicates.path("/aggregate/pet-service/v3/api-docs"), HandlerFunctions.http(petServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("petServiceSwaggerCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .filter(setPath("/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> appointmentServiceRoute() {
        String appointmentServiceUrl = loadBalancerClient.choose(appointmentServiceName).getUri().toString();

        return GatewayRouterFunctions.route("appointment_service")
                .route(RequestPredicates.path("/api/appointment/**"), HandlerFunctions.http(appointmentServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("appointmentServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> appointmentServiceSwaggerRoute() {
        String appointmentServiceUrl = loadBalancerClient.choose(appointmentServiceName).getUri().toString();

        return GatewayRouterFunctions.route("appointment_service_swagger")
                .route(RequestPredicates.path("/aggregate/appointment-service/v3/api-docs"), HandlerFunctions.http(appointmentServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("appointmentServiceSwaggerCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .filter(setPath("/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> medicalRecordServiceRoute() {
        String medicalRecordServiceUrl = loadBalancerClient.choose(medicalRecordServiceName).getUri().toString();

        return GatewayRouterFunctions.route("medical_record_service")
                .route(RequestPredicates.path("/api/record/**"), HandlerFunctions.http(medicalRecordServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("medicalRecordServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> medicalRecordServiceSwaggerRoute() {
        String medicalRecordServiceUrl = loadBalancerClient.choose(medicalRecordServiceName).getUri().toString();

        return GatewayRouterFunctions.route("medical_record_service_swagger")
                .route(RequestPredicates.path("/aggregate/medical-record-service/v3/api-docs"), HandlerFunctions.http(medicalRecordServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("medicalRecordServiceSwaggerCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .filter(setPath("/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> fallbackRoute() {
        return route("fallbackRoute")
                .GET("/fallbackRoute", request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body("Service Unavailable, please try again later"))
                .build();
    }
}
