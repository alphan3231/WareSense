# Changelog

All notable changes to this project will be documented in this file.

## [0.6.0] - 2026-01-02
### Added
- **Order Management System**:
    - Created `OrderController` for managing orders (Create, List, Update).
    - Implemented `OrderService` with logic for creating orders and updating status.
    - Updated `InventoryService` to deduct stock upon order creation (`removeStock`).
    - Added `OrderDto` and `CreateOrderRequest` DTOs.
    - Refactored `Order` entity to include `OrderItem` list.

## [0.5.0] - 2025-12-30
### Added
- **Frontend Served via Spring Boot**:
    - Moved frontend files to `src/main/resources/static`.
    - Frontend accessible directly from `http://localhost:8080`.
- **Auth Improvements**:
    - Login now supports both **username** and **email**.
    - Added `findByEmail` to `UserRepository`.
    - Updated `CustomUserDetailsService` to search by username OR email.
- **Role Seeding**:
    - Default roles (`ROLE_ADMIN`, `ROLE_MANAGER`, `ROLE_COURIER`) are now auto-created on startup.
- **Debug Logging**:
    - Added login attempt logging to `AuthService`.

### Fixed
- Fixed registration failure due to missing roles in database.
- Fixed login failure when user entered email instead of username.

## [0.4.0] - 2025-12-30
### Added
- **Frontend (Lightweight)**:
    - `frontend/index.html`: Login and Registration page using Tailwind CSS (CDN).
    - `frontend/dashboard.html`: Dashboard for Product management and Reports.
    - Implemented API integration using Fetch API.
- **Backend Configuration**:
    - Enabled CORS for all origins to support separated frontend.
    - Added `run.sh` script for automated setup (Java check, Docker start, Build & Run).
    - Added a startup banner with API and Swagger URLs.
- **Dependencies**:
    - Fixed missing `springdoc-openapi-starter-webmvc-ui` and `jjwt-jackson`.

## [0.3.0] - 2025-12-30
### Added
- **Core Domain Logic**:
    - Services & Controllers for `Product`, `Warehouse` (Zones/Shelves), and `Inventory`.
    - Implemented capacity checking logic for shelves.
- **Intelligence & Reporting**:
    - **RoutingService**: TSP algorithm for optimal picking routes.
    - **QrCodeService**: ZXing integration for generating QR codes.
    - **ReportService**: JasperReports integration for PDF inventory reports.
- **Documentation**:
    - Swagger UI enabled and fixed security access (`/swagger-ui/**`).

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
