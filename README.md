# WareSense - Intelligent Warehouse Management System

WareSense is a modern, high-performance Warehouse Management System (WMS) built with Spring Boot and PostgreSQL. It is designed to optimize warehouse operations through intelligent routing, real-time inventory tracking, and efficient order processing.

## 🚀 Features

- **User Management & Security**:
    - secure JWT-based Authentication.
    - Role-Based Access Control (Admin, Warehouse Manager, Courier).
- **Core Domain (In Progress)**:
    - Product & Category Management.
    - Warehouse Mapping (Zones, Shelves, Locations).
    - Real-time Inventory Tracking with Capacity logic.
- **Intelligence (Planned)**:
    - Optimized Picking Routes (Traveling Salesman Problem implementation).
    - QR/Barcode generation for products and shelves.
    - Automated PDF Reporting.

## 💻 Frontend (Lightweight)

This project includes a lightweight frontend using HTML, JS, and Tailwind CSS.
No installation (npm/node) is required.

1.  **Start the Backend** first using `./run.sh`.
2.  Open **`frontend/index.html`** in any web browser.
    - You can simply double-click the file to open it via `file://` protocol.
3.  Register a new user (select a Role) and Login.
4.  Access the Dashboard to manage products.

## 🛠 Tech Stack

- **Backend**: Java 17, Spring Boot 3, Spring Security, Spring Data JPA
- **Database**: PostgreSQL 15 (Dockerized)
- **Tools**: Maven, Docker, Lombok, MapStruct, ZXing (Planned), JasperReports (Planned)

## 📦 Installation & Setup

### Prerequisites

- Java 17 or higher
- Docker & Docker Compose
- Maven

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/WareSense.git
   cd WareSense
   ```

2. **Start the Database**
   Use Docker Compose to spin up the PostgreSQL database.
   ```bash
   docker-compose up -d
   ```

3. **Build the Project**
   ```bash
   mvn clean install
   ```

4. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```
   The API will be available at `http://localhost:8080`.

## 🔑 Default Roles

- **ADMIN**: Full system access.
- **WAREHOUSE_MANAGER**: Can manage stock, products, and shelves.
- **COURIER**: View assigned orders and update delivery status.

## 📝 API Documentation

Swagger UI is available at:
`http://localhost:8080/swagger-ui/index.html` (Once the application is running)

---
*Developed by DeepMind Agent for the User.*
