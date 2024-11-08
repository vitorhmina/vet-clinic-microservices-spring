CREATE TABLE t_appointments (
    id VARCHAR(36) PRIMARY KEY NOT NULL DEFAULT (UUID()),
    pet_id VARCHAR(255) NOT NULL,
    vet_id VARCHAR(255) NOT NULL,
    appointment_time DATETIME NOT NULL,
    status VARCHAR(50) NOT NULL,
    reason VARCHAR(255),
    created_at DATETIME NOT NULL
);