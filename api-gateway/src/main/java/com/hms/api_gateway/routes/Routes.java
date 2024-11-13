package com.hms.api_gateway.routes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

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
                .route(RequestPredicates.path("/api/pet"), HandlerFunctions.http(petServiceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> appointmentServiceRoute() {
        String appointmentServiceUrl = loadBalancerClient.choose(appointmentServiceName).getUri().toString();

        return GatewayRouterFunctions.route("appointment_service")
                .route(RequestPredicates.path("/api/appointment"), HandlerFunctions.http(appointmentServiceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> medicalRecordServiceRoute() {
        String medicalRecordServiceUrl = loadBalancerClient.choose(medicalRecordServiceName).getUri().toString();

        return GatewayRouterFunctions.route("medical_record_service")
                .route(RequestPredicates.path("/api/record"), HandlerFunctions.http(medicalRecordServiceUrl))
                .build();
    }
}
