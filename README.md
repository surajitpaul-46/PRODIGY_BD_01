# PRODIGY_BD_01: User Management REST API

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk" alt="Java 21" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.4.3-brightgreen?style=for-the-badge&logo=springboot" alt="Spring Boot 3.4.3" />
  <img src="https://img.shields.io/badge/Gradle-9.7.1-02303A?style=for-the-badge&logo=gradle" alt="Gradle" />
  <img src="https://img.shields.io/badge/Tests-Passing-success?style=for-the-badge&logo=githubactions" alt="Build Status" />
</p>

A production-ready RESTful API built with **Java 21** and **Spring Boot 3** to perform full **CRUD (Create, Read, Update, Delete)** operations on a `users` resource.

This application uses a thread-safe in-memory data store (`ConcurrentHashMap`), enforces strict input validation via Jakarta Bean Validation, handles errors centrally with custom HTTP status codes, and includes automated integration tests.

---

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Project Architecture](#-project-architecture)
- [Data Model](#-data-model)
- [API Reference](#-api-reference)
  - [Create User](#1-create-user-post)
  - [Get All Users](#2-get-all-users-get)
  - [Get User by ID](#3-get-user-by-id-get)
  - [Update User](#4-update-user-put)
  - [Delete User](#5-delete-user-delete)
- [Input Validation & Rules](#-input-validation--rules)
- [Error Handling](#-error-handling)
- [Getting Started](#-getting-started)
- [Running Tests](#-running-tests)
- [Postman Collection](#-postman-collection)

---

## 🚀 Features

- **Full CRUD Endpoints**: Create, read (all or single), update, and delete users.
- **UUID Identifiers**: Universally Unique Identifiers auto-generated for primary keys.
- **Thread-Safe In-Memory Store**: Uses `ConcurrentHashMap<UUID, User>` for concurrent read/write operations without needing an external database setup.
- **Robust Input Validation**: Validates user attributes (`name`, `email`, `age`) on incoming HTTP requests.
- **Global Error Handling**: `@RestControllerAdvice` mapping exceptions to standard HTTP status codes (`201`, `200`, `204`, `400`, `404`, `500`).
- **Comprehensive Integration Tests**: Automated `MockMvc` tests covering happy paths, edge cases, missing resources, and invalid payloads.

---

## 🛠️ Tech Stack

- **Language**: Java 21 LTS
- **Framework**: Spring Boot 3.4.3
- **Modules**: `spring-boot-starter-web`, `spring-boot-starter-validation`, `spring-boot-starter-test`
- **Build Tool**: Gradle 9.7.1 (Wrapper included)

---

## 📂 Project Architecture

```text
user-crud-api/
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── controller/
│   │   │   │   └── UserController.java         # REST Controller handling HTTP requests
│   │   │   ├── dto/
│   │   │   │   ├── CreateUserRequest.java      # Request payload DTO for user creation
│   │   │   │   ├── UpdateUserRequest.java      # Request payload DTO for user update
│   │   │   │   └── ErrorResponse.java          # Standardized error response DTO
│   │   │   ├── exception/
│   │   │   │   ├── GlobalExceptionHandler.java # Centralized REST exception handler
│   │   │   │   └── UserNotFoundException.java  # Custom RuntimeException for 404 errors
│   │   │   ├── model/
│   │   │   │   └── User.java                   # Core domain entity (UUID id, name, email, age)
│   │   │   ├── repository/
│   │   │   │   └── UserRepository.java         # In-memory ConcurrentHashMap repository
│   │   │   ├── service/
│   │   │   │   └── UserService.java            # Business logic layer
│   │   │   └── DemoApplication.java            # Main Spring Boot application entrypoint
│   │   └── resources/
│   │       └── application.properties          # Server port & app configuration
│   └── test/
│       └── java/com/example/demo/
│           └── UserControllerTest.java         # MockMvc integration tests
├── build.gradle                                # Build script & dependencies
├── gradlew & gradlew.bat                       # Gradle wrapper scripts
└── README.md                                   # Project documentation
```

---

## 🗂️ Data Model

### User Entity
| Field | Type | Description |
|-------|------|-------------|
| `id` | `UUID` | Auto-generated unique identifier |
| `name` | `String` | Full name of the user (non-blank) |
| `email` | `String` | Valid email address (unique identifier format) |
| `age` | `Integer` | Age of the user (range: 0 - 150) |

---

## 📡 API Reference

Base URL: `http://localhost:8080/api/users`

| Method | Endpoint | Description | Status Code |
|--------|----------|-------------|-------------|
| `POST` | `/api/users` | Create a new user | `201 Created` / `400 Bad Request` |
| `GET` | `/api/users` | Fetch list of all users | `200 OK` |
| `GET` | `/api/users/{id}` | Fetch a user by UUID | `200 OK` / `404 Not Found` |
| `PUT` | `/api/users/{id}` | Update an existing user | `200 OK` / `404 Not Found` / `400 Bad Request` |
| `DELETE` | `/api/users/{id}` | Delete a user by UUID | `204 No Content` / `404 Not Found` |

---

### Endpoints Detail & Examples

#### 1. Create User (`POST`)
- **URL**: `/api/users`
- **Headers**: `Content-Type: application/json`
- **Request Body**:
```json
{
  "name": "Jane Doe",
  "email": "jane.doe@example.com",
  "age": 28
}
```
- **Response (`201 Created`)**:
```json
{
  "id": "c1b2a3d4-e5f6-7890-abcd-ef1234567890",
  "name": "Jane Doe",
  "email": "jane.doe@example.com",
  "age": 28
}
```

---

#### 2. Get All Users (`GET`)
- **URL**: `/api/users`
- **Response (`200 OK`)**:
```json
[
  {
    "id": "c1b2a3d4-e5f6-7890-abcd-ef1234567890",
    "name": "Jane Doe",
    "email": "jane.doe@example.com",
    "age": 28
  }
]
```

---

#### 3. Get User by ID (`GET`)
- **URL**: `/api/users/{id}`
- **Response (`200 OK`)**:
```json
{
  "id": "c1b2a3d4-e5f6-7890-abcd-ef1234567890",
  "name": "Jane Doe",
  "email": "jane.doe@example.com",
  "age": 28
}
```

---

#### 4. Update User (`PUT`)
- **URL**: `/api/users/{id}`
- **Headers**: `Content-Type: application/json`
- **Request Body**:
```json
{
  "name": "Jane Doe Updated",
  "email": "jane.updated@example.com",
  "age": 29
}
```
- **Response (`200 OK`)**:
```json
{
  "id": "c1b2a3d4-e5f6-7890-abcd-ef1234567890",
  "name": "Jane Doe Updated",
  "email": "jane.updated@example.com",
  "age": 29
}
```

---

#### 5. Delete User (`DELETE`)
- **URL**: `/api/users/{id}`
- **Response (`204 No Content`)**: Body is empty.

---

## 🛡️ Input Validation & Rules

The API uses standard Jakarta validation constraints on request bodies:

| Field | Validation Annotations | Constraint Description |
|-------|------------------------|------------------------|
| `name` | `@NotBlank` | Name is required and cannot be empty or whitespace |
| `email` | `@NotBlank`, `@Email` | Must be a valid email format (e.g. `user@example.com`) |
| `age` | `@NotNull`, `@Min(0)`, `@Max(150)` | Must be an integer between `0` and `150` inclusive |

---

## ⚠️ Error Handling

Errors return a consistent JSON response structure across all endpoints:

### Validation Error (`400 Bad Request`)
```json
{
  "timestamp": "2026-10-04T11:25:00.000Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed for one or more fields.",
  "errors": {
    "email": "Email must be a valid email address",
    "age": "Age must be at least 0"
  }
}
```

### Resource Not Found (`404 Not Found`)
```json
{
  "timestamp": "2026-10-04T11:25:00.000Z",
  "status": 404,
  "error": "Not Found",
  "message": "User with id 'c1b2a3d4-e5f6-7890-abcd-ef1234567890' was not found."
}
```

---

## 💻 Getting Started

### Prerequisites
- **Java 21** or higher installed.

### Setup & Run Steps

1. **Clone the repository**:
   ```bash
   git clone https://github.com/surajitpaul-46/PRODIGY_BD_01.git
   cd PRODIGY_BD_01
   ```

2. **Run the Spring Boot Application**:
   - **Windows (PowerShell/CMD)**:
     ```powershell
     .\gradlew.bat bootRun
     ```
   - **Linux / macOS**:
     ```bash
     ./gradlew bootRun
     ```

3. The server will start on **`http://localhost:8080`**.

---

## 🧪 Running Tests

Execute the automated test suite with the Gradle wrapper:

```bash
# Windows
.\gradlew.bat test

# Linux / macOS
./gradlew test
```

Test report files will be generated at `build/reports/tests/test/index.html`.

---

## 📮 Postman Collection

Import the following raw JSON into Postman to quickly test all endpoints:

<details>
<summary>Click to expand Postman Collection JSON</summary>

```json
{
  "info": {
    "name": "User CRUD API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Create User",
      "request": {
        "method": "POST",
        "header": [{"key": "Content-Type", "value": "application/json"}],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"name\": \"Jane Doe\",\n  \"email\": \"jane.doe@example.com\",\n  \"age\": 28\n}"
        },
        "url": { "raw": "http://localhost:8080/api/users", "protocol": "http", "host": ["localhost"], "port": "8080", "path": ["api", "users"] }
      }
    },
    {
      "name": "Get All Users",
      "request": {
        "method": "GET",
        "url": { "raw": "http://localhost:8080/api/users", "protocol": "http", "host": ["localhost"], "port": "8080", "path": ["api", "users"] }
      }
    },
    {
      "name": "Get User by ID",
      "request": {
        "method": "GET",
        "url": { "raw": "http://localhost:8080/api/users/REPLACE_WITH_UUID", "protocol": "http", "host": ["localhost"], "port": "8080", "path": ["api", "users", "REPLACE_WITH_UUID"] }
      }
    },
    {
      "name": "Update User",
      "request": {
        "method": "PUT",
        "header": [{"key": "Content-Type", "value": "application/json"}],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"name\": \"Jane Doe Updated\",\n  \"email\": \"jane.updated@example.com\",\n  \"age\": 29\n}"
        },
        "url": { "raw": "http://localhost:8080/api/users/REPLACE_WITH_UUID", "protocol": "http", "host": ["localhost"], "port": "8080", "path": ["api", "users", "REPLACE_WITH_UUID"] }
      }
    },
    {
      "name": "Delete User",
      "request": {
        "method": "DELETE",
        "url": { "raw": "http://localhost:8080/api/users/REPLACE_WITH_UUID", "protocol": "http", "host": ["localhost"], "port": "8080", "path": ["api", "users", "REPLACE_WITH_UUID"] }
      }
    }
  ]
}
```

</details>

---

## 📜 License

This project is open-source under the [MIT License](LICENSE).
