
# Microservices API for Authentication & User Management

A simple backend microservices-style application built with Java Spring Boot, implementing manual JWT authentication and secure password hashing using BCrypt.

---

## Two Main Components
- **Authentication Service** → Handles user login and JWT token generation  
- **User Management Service** → Handles user registration, profile retrieval, and profile updates  

The system uses **JWT-based Authentication**, passwords are hashed using BCrypt, and protected routes require a valid token.

---

## Features

### Authentication Service
- User Login  
- JWT Token Generation  
- Token Validation for secured endpoints  

### User Management Service
- User Registration  
- Get authenticated user's information  
- Update Profile  
- Secure password hashing with BCrypt  

---

## Prerequisites
- Java 17  
- Maven 3.x  
- MySQL  

---

## Database Configuration

Create a database:

```sql
CREATE DATABASE microservice_db;
```

Update your `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/microservice_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## Running the Application

### Command Line
```sh
mvn spring-boot:run
```

### Visual Studio Code
```sh
./mvnw spring-boot:run
```

---

# API Endpoints

---

## 1. Register User  
**POST** `/users/register`

### Request Body
```json
{
  "name": "Jeisen",
  "email": "jeisen@gmail.com",
  "password": "123456",
  "age": 22
}
```

### Example Response
```json
{
  "message": "User registered successfully",
  "user": {
    "id": 1,
    "name": "Jeisen",
    "email": "jeisen@gmail.com"
  }
}
```

---

## 2. Login User  
**POST** `/auth/login`

### Request Body
```json
{
  "email": "jason@gmail.com",
  "password": "123456"
}
```

### Example Response
```json
{
  "token": "<jwt_token>",
  "user": {
    "id": 1,
    "name": "Jeisen",
    "email": "jeisen@gmail.com"
  }
}
```

---

## 3. Get Authenticated User  
**GET** `/users/me`  
**Authorization:** `Bearer <jwt_token>`

### Example Response
```json
{
  "id": 1,
  "name": "Jason",
  "email": "jason@gmail.com",
  "age": 22
}
```

---

## 4. Update Profile  
**PUT** `/users/{id}`  
**Authorization:** `Bearer <jwt_token>`

### Request Body
```json
{
  "name": "Jason Updated",
  "email": "jason_updated@gmail.com",
  "age": 23
}
```

### Example Response
```json
{
  "message": "Profile updated",
  "user": {
    "id": 1,
    "name": "Jason Updated",
    "email": "jason_updated@gmail.com",
    "age": 23
  }
}
```

---

## Testing the API

Use Postman, Thunder Client, or curl:

1. Register or Login to obtain a JWT token  
2. Use the token in secured endpoints:

```
Authorization: Bearer <jwt_token>
```

Endpoints requiring token:

- `/users/me`  
- `/users/{id}`  

---

## Project Structure

```
/src
 ├─ controller
 ├─ entity
 ├─ repository
 ├─ service
 ├─ security
 └─ MainApplication.java
```

---

## Notes
- JWT is generated manually using a utility class.  
- Passwords are securely stored using BCrypt.  
- All protected routes validate the JWT token before processing the request.  
```
