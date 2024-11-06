CREATE TABLE IF NOT EXISTS t_users (
    id VARCHAR(36) PRIMARY KEY NOT NULL DEFAULT (UUID()),
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),
    date_of_birth DATE,
    gender VARCHAR(10),
    address VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(50),
    postal_code VARCHAR(20),
    country VARCHAR(100),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_type VARCHAR(20) NOT NULL
);

INSERT INTO t_users (first_name, last_name, email, password, phone_number, date_of_birth, gender, address, city, state, postal_code, country, created_at, updated_at, deleted, user_type)
VALUES
    ('John', 'Doe', 'john.doe@example.com', 'password123', '123-456-7890', '1990-01-01', 'Male', '123 Main St', 'Anytown', 'CA', '90210', 'USA', NOW(), NOW(), FALSE, 'PATIENT'),
    ('Alice', 'Smith', 'alice.smith@example.com', 'password123', '098-765-4321', '1985-05-15', 'Female', '456 Oak St', 'Othertown', 'TX', '75001', 'USA', NOW(), NOW(), FALSE, 'PATIENT'),
    ('James', 'Brown', 'james.brown@example.com', 'password123', '555-555-5555', '1980-07-20', 'Male', '789 Pine St', 'Thirdtown', 'NY', '10001', 'USA', NOW(), NOW(), FALSE, 'DOCTOR');


