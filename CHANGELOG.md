# Changelog

All notable changes to this project will be documented in this file.

## [Unreleased]

## [0.2.0] - 2025-12-30
### Added
- **Security Layer**:
    - JWT (JSON Web Token) utility for secure token generation and validation.
    - `JwtAuthenticationFilter` to intercept and validate requests.
    - `SecurityConfig` with stateless session management and CSRF protection.
    - `AuthService` and `AuthController` for User Registration and Login.
    - `CustomUserDetailsService` for loading users from the database.
- **Role-Based Access Control (RBAC)**:
    - Defined roles structure (`ROLE_ADMIN`, `ROLE_MANAGER`, `ROLE_COURIER`).
    - Protected endpoints based on authentication status.

## [0.1.0] - 2025-12-30
### Added
- **Project Setup**:
    - Initialized Spring Boot project with Maven.
    - configured `docker-compose.yml` for PostgreSQL 15.
    - Set up project structure and dependencies (Web, Data JPA, Security, Lombok, Validation).
- **Database Schema & Entities**:
    - Designed ER Diagram.
    - Implemented Entities: `User`, `Role`, `Product`, `Category`, `WarehouseZone`, `Shelf`, `InventoryItem`, `Order`, `OrderItem`.
    - Implemented Repositories: `UserRepository`, `RoleRepository`, `ProductRepository`, etc.
