# User Management REST API (Spring Boot)

[![Java Version](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Build Status](https://img.shields.io/badge/Build-Passing-success.svg)](#testing)

A RESTful API built with **Java 21** and **Spring Boot 3** that performs full CRUD (Create, Read, Update, Delete) operations on a `users` resource. It uses a thread-safe in-memory data store (`ConcurrentHashMap`), enforces strict input validation using Jakarta Bean Validation, and returns standardized HTTP status codes and structured JSON error responses.

---

## 📌 Features

- **Full CRUD Capabilities**:
  - `POST`: Create a new user with auto-generated UUID.
  - `GET`: Retrieve all users or fetch a specific user by UUID.
  - `PUT`: Update existing user details by UUID.
  - `DELETE`: Remove a user by UUID.
- **In-Memory Data Structure**: Uses `ConcurrentHashMap<UUID, User>` to handle concurrent read/write operations efficiently without external database overhead.
- **Input Validation**:
  - `name`: Non-blank string requirement (`@NotBlank`).
  - `email`: Valid email format constraint (`@Email`, `@NotBlank`).
  - `age`: Non-null integer bounded between `0` and `150` (`@NotNull`, `@Min`, `@Max`).
- **Comprehensive Error Handling**:
  - `201 Created` for successful resource creation.
  - `200 OK` for successful retrieval and updates.
  - `204 No Content` for successful deletion.
  - `400 Bad Request` for invalid JSON, malformed UUIDs, or failed field validation.
  - `404 Not Found` for requests specifying a non-existent user UUID.
- **Automated Integration Testing**: Includes a full Spring Boot MockMvc test suite covering success paths, validation edge cases, and missing user scenarios.

---

## 📂 Project Architecture

```text
user-crud-api/
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── controller/
│   │   │   │   └── UserController.java         # REST Controller mapping /api/users
│   │   │   ├── dto/
│   │   │   │   ├── CreateUserRequest.java      # Creation DTO with Bean Validation
│   │   │   │   ├── UpdateUserRequest.java      # Update DTO with Bean Validation
│   │   │   │   └── ErrorResponse.java          # Standardized error payload
│   │   │   ├── exception/
│   │   │   │   ├── GlobalExceptionHandler.java # @RestControllerAdvice for HTTP status mapping
│   │   │   │   └── UserNotFoundException.java  # Custom RuntimeException for missing users
│   │   │   ├── model/
│   │   │   │   └── User.java                   # User domain entity (id, name, email, age)
│   │   │   ├── repository/
│   │   │   │   └── UserRepository.java         # In-memory ConcurrentHashMap repository
│   │   │   ├── service/
│   │   │   │   └── UserService.java            # Business logic service
│   │   │   └── DemoApplication.java            # Spring Boot application entry point
│   │   └── resources/
│   │       └── application.properties          # Server configuration
│   └── test/
│       └── java/com/example/demo/
│           └── UserControllerTest.java         # MockMvc integration tests
├── build.gradle                                # Gradle build & dependencies configuration
├── gradlew & gradlew.bat                       # Gradle executable wrapper
└── README.md                                   # Project documentation
```

---

## 🚀 API Endpoints Specification

| Method | Endpoint | Description | Request Body | Expected HTTP Status |
|--------|----------|-------------|--------------|----------------------|
| `POST` | `/api/users` | Create new user | `CreateUserRequest` JSON | `201 Created` / `400 Bad Request` |
| `GET` | `/api/users` | Retrieve all users | None | `200 OK` |
| `GET` | `/api/users/{id}` | Retrieve user by UUID | None | `200 OK` / `404 Not Found` |
| `PUT` | `/api/users/{id}` | Update existing user | `UpdateUserRequest` JSON | `200 OK` / `404 Not Found` / `400 Bad Request` |
| `DELETE` | `/api/users/{id}` | Delete user by UUID | None | `204 No Content` / `404 Not Found` |

---

## 📝 Example Payloads & Requests

### 1. Create User (`POST /api/users`)
**Request Body**:
```json
{
  "name": "Jane Doe",
  "email": "jane.doe@example.com",
  "age": 28
}
```

**Success Response (`201 Created`)**:
```json
{
  "id": "e4a7b2c9-8d1e-4f3a-9b5c-6d7e8f9a0b1c",
  "name": "Jane Doe",
  "email": "jane.doe@example.com",
  "age": 28
}
```

### 2. Validation Error Response (`400 Bad Request`)
If an invalid email or negative age is supplied:
```json
{
  "timestamp": "2026-10-04T11:20:00.000Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed for one or more fields.",
  "errors": {
    "email": "Email must be a valid email address",
    "age": "Age must be at least 0"
  }
}
```

### 3. Not Found Response (`404 Not Found`)
If searching or updating a non-existent UUID:
```json
{
  "timestamp": "2026-10-04T11:20:00.000Z",
  "status": 404,
  "error": "Not Found",
  "message": "User with id '00000000-0000-0000-0000-000000000000' was not found."
}
```

---

## 🧪 Testing

Run the integration test suite using the Gradle wrapper:

```bash
# On Windows
.\gradlew.bat test

# On Linux / macOS
./gradlew test
```

---

## ⚙️ Running Locally

Start the Spring Boot development server on `http://localhost:8080`:

```bash
# On Windows
.\gradlew.bat bootRun

# On Linux / macOS
./gradlew bootRun
```
