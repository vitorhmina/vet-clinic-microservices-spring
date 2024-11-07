CREATE TABLE IF NOT EXISTS t_users (
    id VARCHAR(36) PRIMARY KEY NOT NULL DEFAULT (UUID()),
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_type VARCHAR(20) NOT NULL
);

INSERT INTO t_users (first_name, last_name, email, password, phone_number, created_at, deleted, user_type)
VALUES
    ('John', 'Doe', 'john.doe@example.com', '$2a$10$nxf3XMvgEi3a6wXr85noDOsD697zsYo3QADw6S7pvyXw7qlr8utA2', '123-456-7890', CURRENT_TIMESTAMP, FALSE, 'ADMIN'),
    ('Alice', 'Smith', 'alice.smith@example.com', '$2a$10$K6wYLy.VIPrbgN2RGobF3.0olhp4KfhtEJ/0qsUicS5PJIQDDpqPm', '098-765-4321', CURRENT_TIMESTAMP, FALSE, 'VET'),
    ('James', 'Brown', 'james.brown@example.com', '$2a$10$msoZbQSeJUXIxAnATVmd1OS7SDeqj.tTUGCWQVo0rTRj0vh/nsGrm', '555-555-5555', CURRENT_TIMESTAMP, FALSE, 'CLIENT');

