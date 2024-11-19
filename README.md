# Pet Clinic Management System - Spring Microservices

## Overview

<p align="center">
  <img src="https://i.imgur.com/qZMiZlr.png">
</p>

This project is a **backend-only implementation** of a **Pet Clinic Management System** developed using **Spring Boot** and **microservices architecture**. The application enables seamless management of pet appointments, medical records, notifications, and authentication. Below is a breakdown of the project's structure, tools, and functionality.

---

## Architecture Overview
The project is composed of the following microservices:

### 1. **Pet Service**
   - **Purpose**: Manages information about pets.
   - **Database**: MongoDB.
   - **Communication**: Exposed endpoints for CRUD operations, secured via the authentication server.

### 2. **Appointment Service**
   - **Purpose**: Handles appointment bookings for pets.
   - **Database**: MySQL.
   - **Communication**: 
	   - Exposed endpoints for CRUD operations, secured via the authentication server.
	   - Interacts with Kafka for asynchronous notifications.

### 3. **Medical Record Service**
   - **Purpose**: Manages medical records of pets, including treatments and health history.
   - **Database**: MongoDB.
   - **Communication**: Exposed endpoints for CRUD operations, secured via the authentication server.

### 4. **Notification Service**
   - **Purpose**: Sends email notifications for appointments and important updates.
   - **Communication**: Listens to Kafka events and processes messages for notifications.

### 5. **Auth Server**
   - **Purpose**: Implements user authentication and authorization using **Keycloak**.
   - **Role**: Issues JWT tokens for securing communication between services.

### 6. **API Gateway**
   - **Purpose**: Acts as the central entry point for users, routing requests to the appropriate services.
   - **Features**: Handles token validation and simplifies client-side interactions.

### 7. **Eureka Server**
   - **Purpose**: Service discovery for dynamic registration and locating microservices within the ecosystem.

---

## Tools and Technologies

### **Backend Framework**
- **Spring Boot**: For building robust microservices.

### **Authentication**
- **Keycloak**: Used for securing endpoints and implementing role-based access control (RBAC).

### **Databases**
- **MongoDB**: For Pet and Medical Record services.
- **MySQL**: For Appointment service.

### **Asynchronous Communication**
- **Kafka**: For event-driven messaging between services, particularly for the Notification service.

### **Monitoring and Observability**
- **Prometheus**: For metrics collection.
- **Grafana**: For creating visual dashboards.
- **Grafana Loki**: For logging.
- **Grafana Tempo**: For distributed tracing.
- **OpenTelemetry**: For tracing instrumentation.

---

## Deployment
The entire application is containerized using **Docker** to ensure consistent and portable deployment. All services are deployed as Docker containers, with configurations for:
- Databases
- Kafka
- Keycloak
- Monitoring tools (Prometheus, Grafana, Loki, Tempo).

---

## Postman API Documentation
The Postman schema provides detailed information about the available APIs and their expected payloads. Use the provided Postman collection to test the APIs:
1. Import the schema into Postman.
2. Set the required authentication tokens using the Keycloak Auth server.
3. Explore APIs for:
   - Pet management
   - Appointment booking
   - Medical records
