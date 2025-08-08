# 💳 Bank Card Management System

A backend application built with **Java (Spring Boot)** for managing bank cards.
It allows administrators to manage users and cards, and users to perform secure operations with their own cards such as viewing balances, transferring funds, and requesting card blocks.
Security is ensured via **JWT authentication, role-based access,** and **data encryption**.


____

## Features

### 🔐 Authentication & Authorization

- Spring Security + JWT + DTO
- Roles: ADMIN and USER

### 👨‍💼 Admin

- Create, block, activate, delete cards
- CRUD operations for users
- View all cards

### 👤 User

- View own cards (search + pagination)
- Request card blocking
- Transfer funds between own cards
- Check balance

### 📡 API

- CRUD for cards
- Transfers between own cards
- Filtering & pagination
- Validation & error handling

### 🛡 Security

- Data encryption
- Role-based access control
- Card number masking
____

### 🗄 Tech Stack

**Java 21, Spring Boot, Spring Security, JWT, MySQL, Liquibase, jUnit tests, Docker**

### 📜 API Documentation

[Swagger API Docs](http://localhost:8080/swagger-ui.html)

____

## 🐳 How to Run

1. **Set environment variables**
Create a .env file or set them directly in your environment:

    ```
    JWT_SECRET=your_generated_jwt_secret
    MYSQL_HOST=localhost
    MYSQL_PORT=3306
    MYSQL_DB=bankrest
    MYSQL_USER=root
    MYSQL_PASSWORD=your_password

2. **Build & Run with Docker Compose**

    ```
    docker-compose up --build
    ```

### 📌 Project Structure

```
src/
├── main/java/com/example/bankcards
│    ├── config        # Security & application config
│    ├── controller    # REST controllers
│    ├── dto           # Data Transfer Objects
│    ├── entity        # JPA entities
│    ├── exception     # Custom exception handlers
│    ├── repository    # Spring Data JPA repositories
│    ├── security      # JWT authentication & authorization
│    ├── service       # Business logic
│    └── util          # Utility classes
└── test/java          # Unit tests
```