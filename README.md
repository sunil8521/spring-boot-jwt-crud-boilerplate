# Clean REST API CRUD & JWT Auth Template

A production-grade, highly secure, and clean REST API build using **Spring Boot**, **Spring Security 6.x**, **JJWT 0.13.0**, and **PostgreSQL**. 

Designed as a clean architectural boilerplate for a portfolio/resume, this project implements a unidirectional relational mapping of entities, stateless authentication, structured DTO data transfer, and robust global exception handling.

## 🚀 Features

- **Stateless Authentication**: Pure token-based authorization via custom JWT implementation. Tokens are transmitted in the HTTP `Authorization` header as `Bearer` tokens.
- **Unidirectional Relational Mapping**: Clean `@ManyToOne` relationship from `Product` to `User` to avoid circular serialization dependencies during entity-to-JSON mapping.
- **Clean Service Architecture**: Zero business or authentication logic resides inside the controllers. Services process user registers/log-ins, returning descriptive, secure DTO response models.
- **Robust Validation & Error Handling**: Dynamic input validations using Jakarta Bean Validation. Exceptions are handled globally by a centralized `@RestControllerAdvice` mapping validation fields and custom errors consistently.
- **API Documentation Index**: A default mapping at the `/` route serves JSON metadata detailing all project endpoints, descriptions, and authentication flows.

---

## 🛠️ Tech Stack

- **Backend Framework**: Java 21, Spring Boot 4.1.0
- **Security & Tokens**: Spring Security 6.x, JJWT (Java JWT) 0.13.0
- **Database Layer**: PostgreSQL, Spring Data JPA, HikariCP
- **Lombok**: Boilerplate reduction (Getters, Setters, Constructors)

---

## 📂 Project Structure

```text
com.CURD.curd_operation/
│
├── config/
│   └── SecurityConfig.java            # Spring Security and PasswordEncoder Beans
│
├── controller/
│   ├── AuthController.java            # Account registration and token logins
│   ├── ProductController.java         # Secure CRUD operations for products
│   └── IndexController.java           # Info summary API route at `/`
│
├── dto/
│   ├── SignupRequest.java             # Validation model for creating accounts
│   ├── LoginRequest.java              # Validation model for logging in
│   ├── AuthResponseDto.java           # Clean security payload (token and message)
│   ├── UserDto.java                   # Sanitized user entity representation
│   ├── ProductDto.java                # Output representation of a product (unidirectional)
│   ├── CreateProductDto.java          # Product creation payload
│   ├── PatchProductDto.java           # Partial modification payload
│   └── ErrorResponseDto.java          # Uniform error payload
│
├── entities/
│   ├── User.java                      # Account entity mapping
│   ├── Product.java                   # Product entity mapping
│   └── Role.java                      # User roles enum (USER, ADMIN)
│
├── exception/
│   ├── GlobalExceptionHandler.java    # Centralized controller advice
│   ├── ResourceNotFoundException.java # Thrown when database matches fail
│   └── UnauthorizedAccessException.java # Thrown when user edits unowned products
│
├── repository/
│   ├── UserRepository.java            # User DB operations
│   └── ProductRepository.java         # Product DB operations
│
└── service/
    ├── AuthService.java               # Houses security/token logic
    └── ProductService.java             # Product CRUD & ownership validations
```

---

## 📝 API Endpoints

### Public Paths
* **`GET /`**: Retreives documentation metadata and security flows.
* **`POST /api/v1/auth/register`**: Registers a new user. Returns a JWT token.
* **`POST /api/v1/auth/login`**: Authenticates credentials. Returns a JWT token.

### Protected Paths (Requires Header `Authorization: Bearer <jwt_token>`)
* **`GET /api/v1/products`**: Fetch all products in the database.
* **`GET /api/v1/products/{id}`**: Fetch details of a single product.
* **`POST /api/v1/products`**: Create a product (owner is automatically set to the logged-in user).
* **`PATCH /api/v1/products/{id}`**: Update product name (Only allowable if the current user owns it, or is an `ADMIN`).
* **`DELETE /api/v1/products/{id}`**: Delete product (Only allowable if the current user owns it, or is an `ADMIN`).

---

## 🔄 Authentication Flow

1. **Sign Up / Log In**: Make a request to `/api/v1/auth/register` or `/api/v1/auth/login`.
   ```json
   {
     "username": "developer",
     "password": "strongpassword"
   }
   ```
2. **Obtain Token**: The response will yield a clean `AuthResponseDto`:
   ```json
   {
     "token": "eyJhbGciOiJIUzI1NiJ9.ey...",
     "message": "User login successful"
   }
   ```
3. **Include Token in Header**: Add the header for any CRUD requests to `/api/v1/products`:
   ```text
   Authorization: Bearer <your_jwt_token>
   ```
